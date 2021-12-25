package com.egorshustov.vpoiske.ui.navigation

sealed class SearchScreen(val screenRoute: String) {

    object Main : SearchScreen("search_main")

    object Params : SearchScreen("search_params")

    companion object {
        val graphRoute = "search"
    }
}