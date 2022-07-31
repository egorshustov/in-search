package com.egorshustov.vpoiske.feature.search.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.egorshustov.vpoiske.core.navigation.AppNavigationDestination
import com.egorshustov.vpoiske.feature.search.main_search.MainSearchRoute

object SearchDestination : AppNavigationDestination {
    override val route = "search_route"
    override val destination = "search_destination"
}

fun NavGraphBuilder.searchGraph(
    navigateToAuth: () -> Unit,
    navigateToParams: () -> Unit
) {
    navigation(
        route = SearchDestination.route,
        startDestination = SearchDestination.destination,
    ) {
        composable(route = SearchDestination.destination) {
            MainSearchRoute(
                requireAuth = navigateToAuth,
                onStartNewSearchClick = navigateToParams
            )
        }
    }
}