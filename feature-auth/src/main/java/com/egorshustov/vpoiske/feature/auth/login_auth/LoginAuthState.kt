package com.egorshustov.vpoiske.feature.auth.login_auth

internal data class LoginAuthState(
    val needToFinishAuth: Boolean = false,
    val isLoading: Boolean = false,
    val typedLoginText: String = "",
    val typedPasswordText: String = ""
)