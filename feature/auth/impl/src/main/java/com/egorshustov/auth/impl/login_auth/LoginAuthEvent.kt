package com.egorshustov.auth.impl.login_auth

internal sealed interface LoginAuthEvent {

    data class OnUpdateLogin(val login: String) : LoginAuthEvent

    data class OnUpdatePassword(val password: String) : LoginAuthEvent

    object OnStartAuthProcess : LoginAuthEvent

    data class OnAuthDataObtained(val userId: String, val accessToken: String) : LoginAuthEvent

    object OnAuthError : LoginAuthEvent
}