package com.egorshustov.search.impl.main_search

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.egorshustov.core.common.domain.GetAccessTokenUseCase
import com.egorshustov.core.common.model.data
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class MainSearchViewModel @Inject constructor(
    getAccessTokenUseCase: GetAccessTokenUseCase
) : ViewModel() {

    private val _state: MutableState<MainSearchState> = mutableStateOf(MainSearchState())
    val state: State<MainSearchState> = _state

    init {
        viewModelScope.launch {
            getAccessTokenUseCase(Unit).collect {
                val accessToken = it.data
                _state.value = state.value.copy(isAuthRequired = accessToken.isNullOrBlank())
            }
        }
    }

    fun onTriggerEvent(event: MainSearchEvent) {

    }
}