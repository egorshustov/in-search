package com.egorshustov.vpoiske.feature.auth

import androidx.compose.runtime.Immutable
import com.egorshustov.vpoiske.core.ui.api.UiMessage

@Immutable
internal data class AuthState(
    val needToFinishAuth: Boolean = false,
    val typedLoginText: String = "",
    val typedPasswordText: String = "",
    val isLoading: Boolean = false,
    val message: UiMessage? = null
)