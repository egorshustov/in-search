package com.egorshustov.vpoiske.feature.params

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.egorshustov.vpoiske.core.common.model.Result
import com.egorshustov.vpoiske.core.common.model.data
import com.egorshustov.vpoiske.core.domain.city.GetCitiesUseCase
import com.egorshustov.vpoiske.core.domain.city.GetCitiesUseCaseParams
import com.egorshustov.vpoiske.core.domain.country.GetCountriesStreamUseCase
import com.egorshustov.vpoiske.core.domain.country.RequestCountriesUseCase
import com.egorshustov.vpoiske.core.domain.country.RequestCountriesUseCaseParams
import com.egorshustov.vpoiske.core.domain.token.GetAccessTokenUseCase
import com.egorshustov.vpoiske.core.model.data.City
import com.egorshustov.vpoiske.core.model.data.Country
import com.egorshustov.vpoiske.core.model.data.Gender
import com.egorshustov.vpoiske.core.model.data.Relation
import com.egorshustov.vpoiske.core.model.data.requestsparams.GetCitiesRequestParams
import com.egorshustov.vpoiske.core.model.data.requestsparams.VkCommonRequestParams
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
internal class ParamsViewModel @Inject constructor(
    getAccessTokenUseCase: GetAccessTokenUseCase,
    private val requestCountriesUseCase: RequestCountriesUseCase,
    private val getCountriesStreamUseCase: GetCountriesStreamUseCase,
    private val getCitiesUseCase: GetCitiesUseCase
) : ViewModel() {

    private val _state: MutableState<ParamsState> = mutableStateOf(ParamsState())
    val state: State<ParamsState> = _state

    private val accessTokenFlow: SharedFlow<String> = getAccessTokenUseCase(Unit)
        .mapNotNull {
            if (it.data?.isBlank() == true) {
                _state.value = state.value.copy(
                    authState = state.value.authState.copy(isAuthRequired = true)
                )
            }
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
            ParamsEvent.OnClickResetParamsToDefault -> onClickResetParamsToDefault()
            is ParamsEvent.OnSelectCountry -> onSelectCountry(event.country)
            is ParamsEvent.OnSelectCity -> onSelectCity(event.city)
            is ParamsEvent.OnSelectGender -> onSelectGender(event.gender)
            is ParamsEvent.OnSelectAgeFrom -> onSelectAgeFrom(event.ageFrom)
            is ParamsEvent.OnSelectAgeTo -> onSelectAgeTo(event.ageTo)
            is ParamsEvent.OnSelectRelation -> onSelectRelation(event.relation)
            is ParamsEvent.OnChangeWithPhoneOnly -> onChangeWithPhoneOnly(event.withPhoneOnly)
            is ParamsEvent.OnChangeFoundUsersLimit -> onChangeFoundUsersLimit(event.foundUsersLimit)
            is ParamsEvent.OnChangeDaysInterval -> onChangeDaysInterval(event.daysInterval)
            is ParamsEvent.OnChangeNeedToSetFriendsRange -> onChangeNeedToSetFriendsRange(event.needToSetFriendsRange)
            is ParamsEvent.OnChangeSelectedFriendsRange -> onChangeSelectedFriendsRange(
                event.friendsMinCount,
                event.friendsMaxCount
            )
            is ParamsEvent.OnChangeSelectedFollowersRange -> onChangeSelectedFollowersRange(
                event.followersMinCount,
                event.followersMaxCount
            )
            ParamsEvent.OnClickStartSearch -> onClickStartSearch()
        }
    }

    private fun onAuthRequested() {
        _state.value = state.value.copy(
            authState = state.value.authState.copy(isAuthRequired = false)
        )
    }

    private fun requestCountries() {
        accessTokenFlow
            .onEach { accessToken ->
                requestCountriesUseCase(
                    RequestCountriesUseCaseParams(
                        VkCommonRequestParams(accessToken = accessToken)
                    )
                ).onEach { result ->
                    when (result) {
                        Result.Loading -> {}
                        is Result.Success -> Unit
                        is Result.Error -> {}
                    }
                }.launchIn(viewModelScope)
            }.launchIn(viewModelScope)
    }

    private fun onClickResetParamsToDefault() {
        val savedCountries = state.value.countriesState.countries
        _state.value = ParamsState(countriesState = CountriesState(savedCountries))
    }

    private fun onSelectCountry(country: Country?) {
        if (country != state.value.countriesState.selectedCountry) {
            _state.value = state.value.copy(
                countriesState = state.value.countriesState.copy(selectedCountry = country),
                citiesState = state.value.citiesState.copy(selectedCity = null)
            )
            if (country == null) {
                _state.value = state.value.copy(
                    citiesState = state.value.citiesState.copy(cities = emptyList())
                )
            } else {
                getCities(country.id)
            }
        }
    }

    private fun getCities(countryId: Int) {
        getCitiesUseCase(
            GetCitiesUseCaseParams(
                GetCitiesRequestParams(countryId),
                VkCommonRequestParams(accessToken = accessTokenFlow.replayCache.first())
            )
        ).onEach { result ->
            when (result) {
                Result.Loading -> {}
                is Result.Success -> _state.value = state.value.copy(
                    citiesState = state.value.citiesState.copy(cities = result.data)
                )
                is Result.Error -> {}
            }
        }.launchIn(viewModelScope)
    }

    private fun onSelectCity(city: City?) {
        _state.value = state.value.copy(
            citiesState = state.value.citiesState.copy(selectedCity = city)
        )
    }

    private fun onSelectGender(gender: Gender) {
        _state.value = state.value.copy(
            genderState = state.value.genderState.copy(selectedGender = gender)
        )
    }

    private fun onSelectAgeFrom(ageFrom: Int?) {
        _state.value = state.value.copy(
            ageRangeState = state.value.ageRangeState.copy(selectedAgeFrom = ageFrom)
        )

        val ageTo = state.value.ageRangeState.selectedAgeTo
        if (ageFrom == null || ageTo == null) return

        if (ageFrom > ageTo) {
            _state.value = state.value.copy(
                ageRangeState = state.value.ageRangeState.copy(selectedAgeTo = ageFrom)
            )
        }
    }

    private fun onSelectAgeTo(ageTo: Int?) {
        _state.value = state.value.copy(
            ageRangeState = state.value.ageRangeState.copy(selectedAgeTo = ageTo)
        )

        val ageFrom = state.value.ageRangeState.selectedAgeFrom
        if (ageFrom == null || ageTo == null) return

        if (ageFrom > ageTo) {
            _state.value = state.value.copy(
                ageRangeState = state.value.ageRangeState.copy(selectedAgeFrom = ageTo)
            )
        }
    }

    private fun onSelectRelation(relation: Relation) {
        _state.value = state.value.copy(
            relationState = state.value.relationState.copy(selectedRelation = relation)
        )
    }

    private fun onChangeWithPhoneOnly(withPhoneOnly: Boolean) {
        _state.value = state.value.copy(
            extraOptionsState = state.value.extraOptionsState.copy(withPhoneOnly = withPhoneOnly)
        )
    }

    private fun onChangeFoundUsersLimit(foundUsersLimit: Int) {
        _state.value = state.value.copy(
            extraOptionsState = state.value.extraOptionsState.copy(foundUsersLimit = foundUsersLimit)
        )
    }

    private fun onChangeDaysInterval(daysInterval: Int) {
        _state.value = state.value.copy(
            extraOptionsState = state.value.extraOptionsState.copy(daysInterval = daysInterval)
        )
    }

    private fun onChangeNeedToSetFriendsRange(needToSetFriendsRange: Boolean) {
        _state.value = state.value.copy(
            friendsRangeState = state.value.friendsRangeState.copy(needToSetFriendsRange = needToSetFriendsRange)
        )
    }

    private fun onChangeSelectedFriendsRange(friendsMinCount: Int, friendsMaxCount: Int) {
        _state.value = state.value.copy(
            friendsRangeState = state.value.friendsRangeState.copy(
                selectedFriendsMinCount = friendsMinCount,
                selectedFriendsMaxCount = friendsMaxCount
            )
        )
    }

    private fun onChangeSelectedFollowersRange(followersMinCount: Int, followersMaxCount: Int) {
        _state.value = state.value.copy(
            followersRangeState = state.value.followersRangeState.copy(
                selectedFollowersMinCount = followersMinCount,
                selectedFollowersMaxCount = followersMaxCount
            )
        )
    }

    private fun onClickStartSearch() {
        // TODO: save search to DB and obtain searchId
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