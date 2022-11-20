package com.egorshustov.insearch.feature.params.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.egorshustov.insearch.core.navigation.AppNavigationDestination
import com.egorshustov.insearch.feature.params.ParamsRoute

object ParamsDestination : AppNavigationDestination {
    override val route = "params_route"
    override val destination = "params_destination"
}

fun NavGraphBuilder.paramsGraph(
    startSearchProcess: (searchId: Long) -> Unit,
    navigateToAuth: () -> Unit,
    onBackClick: () -> Unit
) {
    navigation(
        route = ParamsDestination.route,
        startDestination = ParamsDestination.destination
    ) {
        composable(route = ParamsDestination.destination) {
            ParamsRoute(
                startSearchProcess = startSearchProcess,
                requireAuth = navigateToAuth,
                onBackClick = onBackClick
            )
        }
    }
}