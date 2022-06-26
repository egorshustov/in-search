package com.egorshustov.vpoiske.feature.auth

internal data class AuthState(
    val needToFinishAuth: Boolean = false,
    val isLoading: Boolean = false,
    val typedLoginText: String = "",
    val typedPasswordText: String = ""
)