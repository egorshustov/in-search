package com.egorshustov.auth.impl

import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.egorshustov.auth.api.AuthFeatureApi
import com.egorshustov.auth.impl.login_auth.LoginAuthScreen
import com.egorshustov.auth.impl.login_auth.LoginAuthViewModel
import com.egorshustov.core.feature_api.routes.AuthScreen

class AuthFeatureImpl : AuthFeatureApi {

    private val authGraphRoute = "auth"

    override fun authGraphRoute(): String = authGraphRoute

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        navGraphBuilder.navigation(
            startDestination = AuthScreen.LOGIN.screenRoute,
            route = authGraphRoute
        ) {
            composable(
                route = AuthScreen.LOGIN.screenRoute
            ) {
                val viewModel = hiltViewModel<LoginAuthViewModel>()
                LoginAuthScreen(
                    state = viewModel.state.value,
                    onTriggerEvent = viewModel::onTriggerEvent,
                    modifier = modifier
                )
            }
        }
    }
}