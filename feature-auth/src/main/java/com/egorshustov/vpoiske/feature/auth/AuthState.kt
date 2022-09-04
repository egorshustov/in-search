package com.egorshustov.vpoiske.feature.auth

import androidx.compose.runtime.Immutable

@Immutable
internal data class AuthState(
    val needToFinishAuth: Boolean = false,
    val isLoading: Boolean = false,
    val typedLoginText: String = "",
    val typedPasswordText: String = ""
)