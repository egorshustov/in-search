package com.egorshustov.auth.api

import androidx.annotation.StringRes

enum class AuthScreen(
    val screenRoute: String,
    @StringRes val titleResId: Int? = null
) {

    LOGIN("auth_login", R.string.auth_login_title);
}