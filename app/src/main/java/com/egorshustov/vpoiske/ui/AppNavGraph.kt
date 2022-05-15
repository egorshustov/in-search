package com.egorshustov.vpoiske.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.egorshustov.auth.api.AuthFeatureApi
import com.egorshustov.core.navigation.register
import com.egorshustov.search.api.SearchFeatureApi

@Composable
fun AppNavGraph(
    authFeatureApi: AuthFeatureApi,
    searchFeatureApi: SearchFeatureApi,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {

    NavHost(
        navController = navController,
        startDestination = authFeatureApi.authRoute()
    ) {

        register(
            featureApi = authFeatureApi,
            navController = navController,
            modifier = modifier
        )

        register(
            featureApi = searchFeatureApi,
            navController = navController,
            modifier = modifier
        )
    }
}