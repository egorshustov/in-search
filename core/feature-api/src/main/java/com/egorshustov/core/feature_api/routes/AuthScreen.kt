package com.egorshustov.core.feature_api.routes

import androidx.annotation.StringRes
import com.egorshustov.core.feature_api.R

enum class AuthScreen(
    val screenRoute: String,
    @StringRes val titleResId: Int? = null
) {

    LOGIN("auth_login", R.string.auth_login_title);
}