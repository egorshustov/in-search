package com.egorshustov.auth.impl.login_auth

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.egorshustov.auth.impl.domain.GetAccessTokenUseCase
import com.egorshustov.auth.impl.domain.SaveAccessTokenUseCase
import com.egorshustov.auth.impl.domain.SaveAccessTokenUseCaseParams
import com.egorshustov.core.common.model.data
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class LoginAuthViewModel @Inject constructor(
    getAccessTokenUseCase: GetAccessTokenUseCase,
    private val saveAccessTokenUseCase: SaveAccessTokenUseCase
) : ViewModel() {

    val state: MutableState<LoginAuthState> = mutableStateOf(LoginAuthState())

    init {
        viewModelScope.launch {
            getAccessTokenUseCase(Unit).collect {
                val accessToken = it.data
            }
        }
    }

    fun onTriggerEvent(event: LoginAuthEvent) {
        when (event) {
            is LoginAuthEvent.OnUpdateLogin -> onUpdateLogin(event.login)
            is LoginAuthEvent.OnUpdatePassword -> onUpdatePassword(event.password)
            LoginAuthEvent.OnStartAuthProcess -> onStartAuthProcess()
            is LoginAuthEvent.OnAuthDataObtained -> onAuthDataObtained(
                event.userId,
                event.accessToken
            )
            LoginAuthEvent.OnAuthError -> onAuthError()
        }
    }

    private fun onUpdateLogin(login: String) {
        state.value = state.value.copy(login = login)
    }

    private fun onUpdatePassword(password: String) {
        state.value = state.value.copy(password = password)
    }

    private fun onStartAuthProcess() {
        state.value = state.value.copy(isLoading = true)
    }

    private fun onAuthDataObtained(userId: String, accessToken: String) {
        viewModelScope.launch {
            saveAccessTokenUseCase(SaveAccessTokenUseCaseParams(accessToken = accessToken))
        }
    }

    private fun onAuthError() {
        state.value = state.value.copy(isLoading = false)
    }
}