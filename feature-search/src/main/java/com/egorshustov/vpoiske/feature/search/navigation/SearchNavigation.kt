package com.egorshustov.vpoiske.feature.search.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.egorshustov.vpoiske.core.navigation.AppNavigationDestination
import com.egorshustov.vpoiske.feature.search.main_search.MainSearchRoute

object SearchDestination : AppNavigationDestination {
    const val searchIdArg = "searchId"
    override val route = "search_route?$searchIdArg={$searchIdArg}"
    override val destination = "search_destination"

    /**
     * Creates destination route for a searchId
     */
    fun createNavigationRoute(searchId: Long): String = "search_route?${searchIdArg}=$searchId"
}

fun NavGraphBuilder.searchGraph(
    navigateToAuth: () -> Unit,
    navigateToParams: () -> Unit
) {
    navigation(
        route = SearchDestination.route,
        startDestination = SearchDestination.destination,
        arguments = listOf(
            navArgument(SearchDestination.searchIdArg) {
                type = NavType.LongType
            }
        )
    ) {
        composable(route = SearchDestination.destination) {
            MainSearchRoute(
                requireAuth = navigateToAuth,
                onStartNewSearchClick = navigateToParams
            )
        }
    }
}