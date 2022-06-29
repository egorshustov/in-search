package com.egorshustov.vpoiske.feature.params

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.egorshustov.vpoiske.core.common.model.data
import com.egorshustov.vpoiske.core.domain.country.GetCountriesStreamUseCase
import com.egorshustov.vpoiske.core.domain.country.RequestCountriesUseCase
import com.egorshustov.vpoiske.core.domain.country.RequestCountriesUseCaseParams
import com.egorshustov.vpoiske.core.domain.token.GetAccessTokenUseCase
import com.egorshustov.vpoiske.core.model.data.Country
import com.egorshustov.vpoiske.core.model.data.requestsparams.VkCommonRequestParams
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
internal class ParamsViewModel @Inject constructor(
    getAccessTokenUseCase: GetAccessTokenUseCase,
    private val requestCountriesUseCase: RequestCountriesUseCase,
    private val getCountriesStreamUseCase: GetCountriesStreamUseCase
) : ViewModel() {

    private val _state: MutableState<ParamsState> = mutableStateOf(ParamsState())
    val state: State<ParamsState> = _state

    private val accessTokenFlow: SharedFlow<String> = getAccessTokenUseCase(Unit)
        .mapNotNull {
            if (it.data?.isBlank() == true) _state.value = state.value.copy(isAuthRequired = true)
            it.data
        }
        .shareIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            replay = 1
        )

    init {
        subscribeCountriesStream()
        onTriggerEvent(ParamsEvent.RequestCountries)
    }

    fun onTriggerEvent(event: ParamsEvent) {
        when (event) {
            ParamsEvent.OnAuthRequested -> onAuthRequested()
            ParamsEvent.RequestCountries -> requestCountries()
            is ParamsEvent.OnSelectCountry -> onSelectCountry(event.country)
        }
    }

    private fun onAuthRequested() {
        _state.value = state.value.copy(isAuthRequired = false)
    }

    private fun requestCountries() {
        accessTokenFlow
            .onEach { accessToken ->
                requestCountriesUseCase(
                    RequestCountriesUseCaseParams(
                        VkCommonRequestParams(accessToken = accessToken)
                    )
                ).collect()
            }.launchIn(viewModelScope)
    }

    private fun onSelectCountry(country: Country) {
        _state.value = state.value.copy(
            countriesState = state.value.countriesState.copy(selectedCountry = country)
        )
    }

    private fun subscribeCountriesStream() {
        getCountriesStreamUseCase(Unit).onEach {
            val countries = it.data
            if (!countries.isNullOrEmpty()) {
                _state.value = state.value.copy(
                    countriesState = state.value.countriesState.copy(countries = countries)
                )
            }
        }.launchIn(viewModelScope)
    }
}