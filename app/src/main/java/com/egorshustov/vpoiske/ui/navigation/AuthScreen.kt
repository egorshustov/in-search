package com.egorshustov.vpoiske.ui.navigation

import androidx.annotation.StringRes
import com.egorshustov.vpoiske.R

enum class AuthScreen(
    val screenRoute: String,
    @StringRes val titleRes: Int? = null
) {

    LOGIN("auth_login", R.string.authorization);

    companion object {
        const val graphRoute = "auth"
    }
}