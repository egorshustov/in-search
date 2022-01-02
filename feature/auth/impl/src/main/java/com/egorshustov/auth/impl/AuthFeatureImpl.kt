package com.egorshustov.auth.impl

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.egorshustov.auth.api.AuthFeatureApi
import com.egorshustov.auth.impl.login_auth.LoginAuthScreen
import com.egorshustov.core.feature_api.routes.AuthScreen

class AuthFeatureImpl : AuthFeatureApi {

    override fun authRoute(): String = ""

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        navGraphBuilder.navigation(
            startDestination = AuthScreen.LOGIN.screenRoute,
            route = AuthScreen.graphRoute
        ) {
            composable(
                route = AuthScreen.LOGIN.screenRoute
            ) { navBackStackEntry ->
                LoginAuthScreen()
            }
        }
    }
}