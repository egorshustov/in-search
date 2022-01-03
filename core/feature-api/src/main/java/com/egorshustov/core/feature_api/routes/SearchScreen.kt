package com.egorshustov.core.feature_api.routes

import androidx.annotation.StringRes
import com.egorshustov.core.feature_api.R

enum class SearchScreen(
    val screenRoute: String,
    @StringRes val titleRes: Int? = null
) {

    MAIN("search_main", R.string.search_main_title),
    PARAMS("search_params", R.string.search_params_title);
}