package com.egorshustov.insearch.feature.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.egorshustov.insearch.core.domain.token.GetAccessTokenUseCase
import com.egorshustov.insearch.core.domain.token.SaveAccessTokenUseCase
import com.egorshustov.insearch.core.domain.token.SaveAccessTokenUseCaseParams
import com.egorshustov.insearch.core.ui.api.UiMessage
import com.egorshustov.insearch.core.ui.api.UiMessageManager
import com.egorshustov.insearch.core.ui.util.ObservableLoadingCounter
import com.egorshustov.insearch.core.ui.util.unwrapResult
import com.egorshustov.insearch.feature.auth.utils.AuthWebViewError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class AuthViewModel @Inject constructor(
    private val getAccessTokenUseCase: GetAccessTokenUseCase,
    private val saveAccessTokenUseCase: SaveAccessTokenUseCase
) : ViewModel() {

    private val loadingState = ObservableLoadingCounter()
    private val uiMessageManager = UiMessageManager()

    private val _state: MutableStateFlow<AuthState> = MutableStateFlow(AuthState.Default)
    val state: StateFlow<AuthState> = _state.asStateFlow()

    init {
        collectAccessToken()
        collectLoadingState()
        collectUiMessageManager()
    }

    fun onTriggerEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.OnUpdateLogin -> onUpdateLogin(event.login)
            is AuthEvent.OnUpdatePassword -> onUpdatePassword(event.password)
            AuthEvent.OnStartAuthProcess -> onStartAuthProcess()
            is AuthEvent.OnAuthDataObtained -> onAuthDataObtained(
                event.userId,
                event.accessToken
            )
            AuthEvent.OnNeedToFinishAuthProcessed -> onNeedToFinishAuthProcessed()
            is AuthEvent.OnAuthError -> onAuthError(event.error)
            is AuthEvent.OnMessageShown -> onMessageShown(event.uiMessageId)
        }
    }

    private fun onUpdateLogin(login: String) {
        _state.update { it.copy(typedLoginText = login.trim()) }
    }

    private fun onUpdatePassword(password: String) {
        _state.update { it.copy(typedPasswordText = password.trim()) }
    }

    private fun onStartAuthProcess() {
        loadingState.addLoader()
        _state.update { it.copy(isAuthInProcess = true) }
    }

    private fun onAuthDataObtained(userId: String, accessToken: String) {
        viewModelScope.launch {
            saveAccessTokenUseCase(SaveAccessTokenUseCaseParams(accessToken = accessToken))
        }
    }

    private fun onNeedToFinishAuthProcessed() {
        _state.update { it.copy(needToFinishAuth = false) }
        loadingState.removeLoader()
    }

    private fun onAuthError(error: AuthWebViewError) {
        _state.update {
            it.copy(
                isAuthInProcess = false,
                message = UiMessage(error.errorResId)
            )
        }
        loadingState.removeLoader()
    }

    private fun onMessageShown(uiMessageId: Long) {
        viewModelScope.launch {
            uiMessageManager.clearMessage(uiMessageId)
        }
    }

    private fun collectAccessToken() {
        getAccessTokenUseCase(Unit)
            .unwrapResult(loadingState, uiMessageManager)
            .onEach { accessToken ->
                _state.update { it.copy(needToFinishAuth = accessToken.isNotBlank()) }
            }.launchIn(viewModelScope)
    }

    private fun collectLoadingState() {
        loadingState.flow.onEach { isLoading ->
            _state.update { it.copy(isLoading = isLoading) }
        }.launchIn(viewModelScope)
    }

    private fun collectUiMessageManager() {
        uiMessageManager.message.onEach { message ->
            _state.update { it.copy(message = message) }
        }.launchIn(viewModelScope)
    }
}