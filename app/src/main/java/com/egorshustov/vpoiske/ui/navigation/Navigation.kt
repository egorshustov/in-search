package com.egorshustov.vpoiske.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.egorshustov.vpoiske.ui.login_auth.LoginAuthScreen
import com.egorshustov.vpoiske.ui.main_search.MainSearchScreen
import com.egorshustov.vpoiske.ui.params_search.ParamsSearchScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = SearchScreen.graphRoute
    ) {
        searchGraph(navController)
        authGraph(navController)
    }
}

fun NavGraphBuilder.searchGraph(navController: NavController) {
    navigation(
        startDestination = SearchScreen.Main.screenRoute,
        route = SearchScreen.graphRoute
    ) {
        composable(
            route = SearchScreen.Main.screenRoute
        ) { navBackStackEntry ->
            MainSearchScreen(
                onLastSearchItemClicked = {},
                onNewSearchItemClicked = { navController.navigate(SearchScreen.Params.screenRoute) },
                onSearchHistoryItemClicked = {},
                onChangeThemeItemClicked = {}
            )
        }
        composable(
            route = SearchScreen.Params.screenRoute
        ) { navBackStackEntry ->
            ParamsSearchScreen()
        }
    }
}

fun NavGraphBuilder.authGraph(navController: NavController) {
    navigation(
        startDestination = AuthScreen.Login.screenRoute,
        route = AuthScreen.graphRoute
    ) {
        composable(
            route = AuthScreen.Login.screenRoute
        ) { navBackStackEntry ->
            LoginAuthScreen()
        }
    }
}