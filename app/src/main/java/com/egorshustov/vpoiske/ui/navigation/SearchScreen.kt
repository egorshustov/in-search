package com.egorshustov.vpoiske.ui.navigation

sealed class SearchScreen(val route: String) {

    object Main: SearchScreen("search_main")

    object Params: SearchScreen("search_params")
}
