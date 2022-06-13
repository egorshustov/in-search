package com.egorshustov.vpoiske.feature.search.main_search

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.egorshustov.vpoiske.core.common.model.Result
import com.egorshustov.vpoiske.core.domain.user.GetLastSearchUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
internal class MainSearchViewModel @Inject constructor(
    val getLastSearchUsersUseCase: GetLastSearchUsersUseCase
) : ViewModel() {

    private val _state: MutableState<MainSearchState> = mutableStateOf(MainSearchState())
    val state: State<MainSearchState> = _state

    init {
        getLastSearchUsers()
    }

    fun onTriggerEvent(event: MainSearchEvent) {
        when (event) {
            MainSearchEvent.OnAuthRequested -> onAuthRequested()
        }
    }

    private fun onAuthRequested() {
        _state.value = state.value.copy(isAuthRequired = false)
    }

    private fun getLastSearchUsers() {
        getLastSearchUsersUseCase(Unit).onEach {
            if (it is Result.Success) {
                _state.value = state.value.copy(users = it.data)
            }
        }.launchIn(viewModelScope)
    }
}