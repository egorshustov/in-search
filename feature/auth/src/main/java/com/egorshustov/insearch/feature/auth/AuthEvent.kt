package com.egorshustov.insearch.feature.auth

import com.egorshustov.insearch.feature.auth.utils.AuthWebViewError

internal sealed interface AuthEvent {

    data class OnUpdateLogin(val login: String) : AuthEvent

    data class OnUpdatePassword(val password: String) : AuthEvent

    object OnStartAuthProcess : AuthEvent

    data class OnClickDemoLogin(val accessToken: String) : AuthEvent

    data class OnAuthDataObtained(val accessToken: String, val userId: String) : AuthEvent

    object OnNeedToFinishAuthProcessed : AuthEvent

    data class OnAuthError(val error: AuthWebViewError) : AuthEvent

    data class OnMessageShown(val uiMessageId: Long) : AuthEvent
}