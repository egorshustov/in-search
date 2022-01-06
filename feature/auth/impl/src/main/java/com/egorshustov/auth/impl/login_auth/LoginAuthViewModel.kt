package com.egorshustov.auth.impl.login_auth

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class LoginAuthViewModel @Inject constructor() : ViewModel() {

    val state: MutableState<LoginAuthState> = mutableStateOf(LoginAuthState())

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
        val (a, b) = Pair(userId, accessToken)
        state.value = state.value.copy(isLoading = false)
    }

    private fun onAuthError() {
        state.value = state.value.copy(isLoading = false)
    }
}