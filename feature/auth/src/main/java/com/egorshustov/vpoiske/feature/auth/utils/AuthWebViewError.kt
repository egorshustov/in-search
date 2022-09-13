package com.egorshustov.vpoiske.feature.auth.utils

import androidx.annotation.StringRes
import com.egorshustov.vpoiske.feature.auth.R

internal enum class AuthWebViewError(@StringRes val errorResId: Int) {

    WRONG_LOGIN_OR_PASSWORD(R.string.error_network_wrong_login_or_password),
    NO_INTERNET_CONNECTION_OR_UNAVAILABLE(R.string.error_network_check_internet_or_unavailable),
    UNDEFINED(R.string.error_something_went_wrong)
}