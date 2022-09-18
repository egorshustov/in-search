package com.egorshustov.feature.history.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.egorshustov.feature.history.searchitem.HistorySearchItemRoute
import com.egorshustov.feature.history.searchlist.HistorySearchListRoute
import com.egorshustov.vpoiske.core.navigation.AppNavigationDestination

object HistoryDestination : AppNavigationDestination {
    override val route = "history_route"
    override val destination = "history_destination"

    // Inner routes:
    internal const val destinationItem = "history_destination_item"
}

fun NavGraphBuilder.historyGraph(
    navController: NavHostController, // navController passed for inner navigation
    onBackClick: () -> Unit
) {
    navigation(
        route = HistoryDestination.route,
        startDestination = HistoryDestination.destination
    ) {
        composable(route = HistoryDestination.destination) {
            HistorySearchListRoute(
                onSearchItemClick = { searchId ->

                },
                onBackClick = onBackClick
            )
        }
        composable(route = HistoryDestination.destinationItem) {
            HistorySearchItemRoute(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}