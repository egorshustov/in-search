package com.egorshustov.insearch.feature.auth

import androidx.compose.runtime.Immutable
import com.egorshustov.insearch.core.ui.api.UiMessage

@Immutable
internal data class AuthState(
    val isAuthInProcess: Boolean = false,
    val needToFinishAuth: Boolean = false,
    val typedLoginText: String = "",
    val typedPasswordText: String = "",
    val isLoading: Boolean = false,
    val message: UiMessage? = null
) {
    companion object {
        val Default = AuthState()
    }
}