package com.egorshustov.vpoiske.feature.auth.navigation

import androidx.annotation.StringRes
import com.egorshustov.vpoiske.feature.auth.R

enum class AuthFeatureScreens(
    val screenRoute: String,
    @StringRes val titleResId: Int? = null
) {
    LOGIN("auth_login", R.string.auth_login_title);
}