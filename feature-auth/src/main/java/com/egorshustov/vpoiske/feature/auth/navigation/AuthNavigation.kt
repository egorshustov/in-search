package com.egorshustov.vpoiske.feature.auth.navigation

import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.egorshustov.vpoiske.core.navigation.AppNavigationDestination
import com.egorshustov.vpoiske.feature.auth.login_auth.LoginAuthScreen
import com.egorshustov.vpoiske.feature.auth.login_auth.LoginAuthViewModel

object AuthDestination : AppNavigationDestination {
    override val screenRoute = AuthScreen.LOGIN.screenRoute
    override val graphDestination = "auth_destination"
}

fun NavGraphBuilder.authGraph(
    navigateToSearch: () -> Unit,
    modifier: Modifier
) {
    navigation(
        startDestination = AuthDestination.screenRoute,
        route = AuthDestination.graphDestination
    ) {
        composable(
            route = AuthDestination.screenRoute
        ) {
            val viewModel = hiltViewModel<LoginAuthViewModel>()
            LoginAuthScreen(
                state = viewModel.state.value,
                onTriggerEvent = viewModel::onTriggerEvent,
                onAuthFinished = navigateToSearch,
                modifier = modifier
            )
        }
    }
}