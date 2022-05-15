package com.egorshustov.auth.impl.login_auth

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.egorshustov.vpoiske.core.domain.SaveAccessTokenUseCase
import com.egorshustov.vpoiske.core.domain.SaveAccessTokenUseCaseParams
import com.egorshustov.core.common.utils.DI_NAME_SEARCH_ROUTE
import com.egorshustov.vpoiske.core.common.model.data
import com.egorshustov.vpoiske.core.domain.GetAccessTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
internal class LoginAuthViewModel @Inject constructor(
    getAccessTokenUseCase: GetAccessTokenUseCase,
    private val saveAccessTokenUseCase: SaveAccessTokenUseCase,
    @Named(DI_NAME_SEARCH_ROUTE) val searchRoute: String
) : ViewModel() {

    private val _state: MutableState<LoginAuthState> = mutableStateOf(LoginAuthState())
    val state: State<LoginAuthState> = _state

    init {
        viewModelScope.launch {
            getAccessTokenUseCase(Unit).collect {
                val accessToken = it.data
                _state.value = state.value.copy(needToFinishAuth = !accessToken.isNullOrBlank())
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
            LoginAuthEvent.OnNeedToFinishAuthProcessed -> onNeedToFinishAuthProcessed()
            LoginAuthEvent.OnAuthError -> onAuthError()
        }
    }

    private fun onUpdateLogin(login: String) {
        _state.value = state.value.copy(typedLoginText = login)
    }

    private fun onUpdatePassword(password: String) {
        _state.value = state.value.copy(typedPasswordText = password)
    }

    private fun onStartAuthProcess() {
        _state.value = state.value.copy(isLoading = true)
    }

    private fun onAuthDataObtained(userId: String, accessToken: String) {
        viewModelScope.launch {
            saveAccessTokenUseCase(SaveAccessTokenUseCaseParams(accessToken = accessToken))
        }
    }

    private fun onNeedToFinishAuthProcessed() {
        _state.value = state.value.copy(needToFinishAuth = false)
    }

    private fun onAuthError() {
        _state.value = state.value.copy(isLoading = false)
    }
}