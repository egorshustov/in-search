package com.egorshustov.feature.history.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.egorshustov.feature.history.searchitem.HistorySearchItemRoute
import com.egorshustov.feature.history.searchlist.HistorySearchListRoute
import com.egorshustov.vpoiske.core.navigation.AppNavigationDestination

object HistoryDestination : AppNavigationDestination {
    override val route = "history_route"
    override val destination = "history_destination"

    // Inner routes and args:
    internal const val searchIdArg = "searchId"
    internal const val destinationItem = "history_destination_item/{$searchIdArg}"
    internal fun createDestinationItemRoute(searchId: Long): String =
        "history_destination_item/$searchId"
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
                    navController.navigate(HistoryDestination.createDestinationItemRoute(searchId))
                },
                onBackClick = onBackClick
            )
        }
        composable(
            route = HistoryDestination.destinationItem,
            arguments = listOf(
                navArgument(HistoryDestination.searchIdArg) {
                    type = NavType.LongType
                }
            )
        ) {
            HistorySearchItemRoute(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}