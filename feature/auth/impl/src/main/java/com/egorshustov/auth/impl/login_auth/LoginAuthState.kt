package com.egorshustov.auth.impl.login_auth

internal data class LoginAuthState(
    val isLoading: Boolean = false,
    val login: String = "",
    val password: String = "",
)