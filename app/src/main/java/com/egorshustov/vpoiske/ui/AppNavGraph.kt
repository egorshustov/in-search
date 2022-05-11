package com.egorshustov.vpoiske.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.egorshustov.auth.api.AuthFeatureApi
import com.egorshustov.core.feature_api.register
import com.egorshustov.search.api.SearchFeatureApi

@Composable
fun AppNavGraph(
    searchFeatureApi: SearchFeatureApi,
    authFeatureApi: AuthFeatureApi,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {

    NavHost(
        navController = navController,
        startDestination = searchFeatureApi.searchRoute()
    ) {

        register(
            featureApi = searchFeatureApi,
            navController = navController,
            modifier = modifier
        )

        register(
            featureApi = authFeatureApi,
            navController = navController,
            modifier = modifier
        )
    }
}