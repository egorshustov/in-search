package com.egorshustov.vpoiske.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.egorshustov.vpoiske.feature.auth.navigation.AuthDestination
import com.egorshustov.vpoiske.feature.auth.navigation.authGraph
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
    startDestination: String = SearchDestination.graphDestination
) {

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {

        searchGraph(
            navigateToAuth = {
                navController.navigate(AuthDestination.graphDestination) {
                    launchSingleTop = true
                    popUpTo(SearchDestination.graphDestination) { inclusive = true }
                }
            },
            modifier = modifier
        )

        authGraph(
            navigateToSearch = {
                navController.navigateUp()
                navController.navigate(SearchDestination.graphDestination) {
                    launchSingleTop = true
                    popUpTo(AuthDestination.graphDestination) { inclusive = true }
                }
            },
            modifier = modifier
        )
    }
}