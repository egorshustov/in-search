package com.egorshustov.auth.impl.login_auth

internal data class LoginAuthState(
    val needToFinishAuth: Boolean = false,
    val isLoading: Boolean = false,
    val typedLoginText: String = "",
    val typedPasswordText: String = ""
)