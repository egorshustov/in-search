package com.egorshustov.vpoiske.feature.auth

import com.egorshustov.vpoiske.feature.auth.utils.AuthWebViewError

internal sealed interface AuthEvent {

    data class OnUpdateLogin(val login: String) : AuthEvent

    data class OnUpdatePassword(val password: String) : AuthEvent

    object OnStartAuthProcess : AuthEvent

    data class OnAuthDataObtained(val userId: String, val accessToken: String) : AuthEvent

    object OnNeedToFinishAuthProcessed : AuthEvent

    data class OnAuthError(val error: AuthWebViewError) : AuthEvent

    data class OnMessageShown(val uiMessageId: Long) : AuthEvent
}