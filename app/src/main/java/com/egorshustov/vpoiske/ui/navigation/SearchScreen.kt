package com.egorshustov.vpoiske.ui.navigation

import androidx.annotation.StringRes
import com.egorshustov.vpoiske.R

enum class SearchScreen(
    val screenRoute: String,
    @StringRes val titleRes: Int? = null
) {

    MAIN("search_main", R.string.app_name),
    PARAMS("search_params", R.string.new_search);

    companion object {
        const val graphRoute = "search"
    }
}