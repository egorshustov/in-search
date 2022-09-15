package com.egorshustov.vpoiske.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.egorshustov.feature.history.navigation.historyGraph
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
    openDrawer: () -> Unit = {},
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
                    popUpTo(SearchDestination.route) { inclusive = true }
                    launchSingleTop = true
                }
            },
            navigateToParams = {
                navController.navigate(ParamsDestination.route)
            },
            openDrawer = openDrawer
        )

        paramsGraph(
            startSearchProcess = { searchId ->
                navController.navigate(SearchDestination.createNavigationRoute(searchId)) {
                    popUpTo(SearchDestination.route) { inclusive = true }
                    launchSingleTop = true
                }
            },
            navigateToAuth = {
                navController.navigate(AuthDestination.route) {
                    popUpTo(ParamsDestination.route) { inclusive = true }
                    launchSingleTop = true
                }
            },
            onBackClick = { navController.popBackStack() }
        )

        authGraph(
            onBackClick = { navController.popBackStack() }
        )

        historyGraph(
            navController = navController,
            onBackClick = { navController.popBackStack() }
        )
    }
}