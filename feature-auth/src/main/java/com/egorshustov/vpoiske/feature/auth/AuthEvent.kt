package com.egorshustov.vpoiske.feature.auth

internal sealed interface AuthEvent {

    data class OnUpdateLogin(val login: String) : AuthEvent

    data class OnUpdatePassword(val password: String) : AuthEvent

    object OnStartAuthProcess : AuthEvent

    data class OnAuthDataObtained(val userId: String, val accessToken: String) : AuthEvent

    object OnNeedToFinishAuthProcessed : AuthEvent

    object OnAuthError : AuthEvent
}