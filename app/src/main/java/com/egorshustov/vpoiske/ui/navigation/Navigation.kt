package com.egorshustov.vpoiske.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.egorshustov.vpoiske.ui.MainSearchScreen
import com.egorshustov.vpoiske.ui.ParamsSearchScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = SearchScreen.Main.route) {
        composable(
            route = SearchScreen.Main.route
        ) { navBackStackEntry ->
            MainSearchScreen()
        }
        composable(
            route = SearchScreen.Params.route
        ) { navBackStackEntry ->
            ParamsSearchScreen()
        }
    }
}