package com.egorshustov.auth.impl.login_auth

internal data class LoginAuthState(
    val isAuthDataObtained: Boolean = false,
    val isLoading: Boolean = false,
    val login: String = "",
    val password: String = ""
)