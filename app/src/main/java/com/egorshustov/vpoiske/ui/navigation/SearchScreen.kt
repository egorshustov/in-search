package com.egorshustov.vpoiske.ui.navigation

enum class SearchScreen(val screenRoute: String) {

    MAIN("search_main"),
    PARAMS("search_params");

    companion object {
        const val graphRoute = "search"
    }
}