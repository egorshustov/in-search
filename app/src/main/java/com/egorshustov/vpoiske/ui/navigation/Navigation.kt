package com.egorshustov.vpoiske.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.egorshustov.vpoiske.ui.main_search.MainSearchScreen
import com.egorshustov.vpoiske.ui.params_search.ParamsSearchScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = SearchScreen.Main.route) {
        composable(
            route = SearchScreen.Main.route
        ) { navBackStackEntry ->
            MainSearchScreen(
                onLastSearchItemClicked = {},
                onNewSearchItemClicked = { navController.navigate(SearchScreen.Params.route) },
                onSearchHistoryItemClicked = {},
                onChangeThemeItemClicked = {}
            )
        }
        composable(
            route = SearchScreen.Params.route
        ) { navBackStackEntry ->
            ParamsSearchScreen()
        }
    }
}