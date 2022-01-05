package com.egorshustov.auth.impl.utils

import androidx.annotation.StringRes
import com.egorshustov.auth.impl.R

internal enum class AuthErrorType(@StringRes val errorResId: Int) {

    WRONG_LOGIN_OR_PASSWORD(R.string.error_wrong_login_or_password),
    NO_INTERNET_CONNECTION_OR_UNAVAILABLE(R.string.error_check_internet_or_unavailable),
    UNDEFINED(R.string.error_something_went_wrong)
}