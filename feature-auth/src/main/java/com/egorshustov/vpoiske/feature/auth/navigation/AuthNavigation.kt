package com.egorshustov.vpoiske.feature.auth.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.egorshustov.vpoiske.core.navigation.AppNavigationDestination
import com.egorshustov.vpoiske.feature.auth.AuthRoute

object AuthDestination : AppNavigationDestination {
    override val route = "auth_route"
    override val destination = "auth_destination"
}

fun NavGraphBuilder.authGraph(
    returnToPreviousScreen: () -> Unit
) {
    navigation(
        route = AuthDestination.route,
        startDestination = AuthDestination.destination
    ) {
        composable(route = AuthDestination.destination) {
            AuthRoute(
                onAuthFinished = returnToPreviousScreen
            )
        }
    }
}