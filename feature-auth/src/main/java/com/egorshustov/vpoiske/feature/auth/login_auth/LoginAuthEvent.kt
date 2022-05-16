package com.egorshustov.vpoiske.feature.auth.login_auth

internal sealed interface LoginAuthEvent {

    data class OnUpdateLogin(val login: String) : LoginAuthEvent

    data class OnUpdatePassword(val password: String) : LoginAuthEvent

    object OnStartAuthProcess : LoginAuthEvent

    data class OnAuthDataObtained(val userId: String, val accessToken: String) : LoginAuthEvent

    object OnNeedToFinishAuthProcessed : LoginAuthEvent

    object OnAuthError : LoginAuthEvent
}