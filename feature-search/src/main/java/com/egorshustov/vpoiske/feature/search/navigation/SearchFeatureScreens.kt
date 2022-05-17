package com.egorshustov.vpoiske.feature.search.navigation

import androidx.annotation.StringRes
import com.egorshustov.vpoiske.feature.search.R

enum class SearchFeatureScreens(
    val screenRoute: String,
    @StringRes val titleResId: Int? = null
) {
    MAIN("search_main", R.string.search_main_title),
    PARAMS("search_params", R.string.search_params_title);
}