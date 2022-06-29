package com.egorshustov.vpoiske.feature.params.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.egorshustov.vpoiske.core.navigation.AppNavigationDestination
import com.egorshustov.vpoiske.feature.params.ParamsRoute

object ParamsDestination : AppNavigationDestination {
    override val route = "params_route"
    override val destination = "params_destination"
}

fun NavGraphBuilder.paramsGraph(
    navigateToAuth: () -> Unit,
    modifier: Modifier
) {
    navigation(
        route = ParamsDestination.route,
        startDestination = ParamsDestination.destination
    ) {
        composable(route = ParamsDestination.destination) {
            ParamsRoute(
                modifier = modifier,
                requireAuth = navigateToAuth
            )
        }
    }
}