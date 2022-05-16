package com.egorshustov.vpoiske.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.egorshustov.vpoiske.feature.auth.navigation.AuthDestination
import com.egorshustov.vpoiske.feature.auth.navigation.authGraph
import com.egorshustov.vpoiske.feature.search.navigation.SearchDestination
import com.egorshustov.vpoiske.feature.search.navigation.searchGraph

@Composable
fun AppNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {

    NavHost(
        navController = navController,
        startDestination = AuthDestination.graphDestination
    ) {

        authGraph(
            navigateToSearch = {
                navController.navigate(SearchDestination.graphDestination) {
                    launchSingleTop = true
                    popUpTo(AuthDestination.graphDestination) { inclusive = true }
                }
            },
            modifier = modifier
        )

        searchGraph(
            navigateToAuth = {
                navController.navigate(AuthDestination.graphDestination) {
                    launchSingleTop = true
                    popUpTo(SearchDestination.graphDestination) { inclusive = true }
                }
            },
            modifier = modifier
        )
    }
}