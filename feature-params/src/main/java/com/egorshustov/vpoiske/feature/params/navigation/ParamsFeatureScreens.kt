package com.egorshustov.vpoiske.feature.params.navigation

import androidx.annotation.StringRes
import com.egorshustov.vpoiske.feature.params.R

enum class ParamsFeatureScreens(
    val screenRoute: String,
    @StringRes val titleResId: Int? = null
) {
    PARAMS("params", R.string.search_params_title)
}