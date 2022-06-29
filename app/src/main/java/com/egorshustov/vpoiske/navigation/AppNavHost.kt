package com.egorshustov.vpoiske.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.egorshustov.vpoiske.feature.auth.navigation.AuthDestination
import com.egorshustov.vpoiske.feature.auth.navigation.authGraph
import com.egorshustov.vpoiske.feature.params.navigation.ParamsDestination
import com.egorshustov.vpoiske.feature.params.navigation.paramsGraph
import com.egorshustov.vpoiske.feature.search.navigation.SearchDestination
import com.egorshustov.vpoiske.feature.search.navigation.searchGraph

/**
 * Top-level navigation graph.
 *
 * The navigation graph defined in this file defines the different top level routes.
 */
@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = SearchDestination.route
) {

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {

        searchGraph(
            navigateToAuth = {
                navController.navigate(AuthDestination.route) {
                    launchSingleTop = true
                    popUpTo(SearchDestination.route) { inclusive = true }
                }
            },
            navigateToParams = {
                navController.navigate(ParamsDestination.route)
            },
            modifier = modifier
        )

        paramsGraph(
            navigateToAuth = {
                navController.navigate(AuthDestination.route) {
                    launchSingleTop = true
                    popUpTo(ParamsDestination.route) { inclusive = true }
                }
            },
            modifier = modifier
        )

        authGraph(
            returnToPreviousScreen = { navController.popBackStack() },
            modifier = modifier
        )
    }
}