package com.egorshustov.search.impl

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.egorshustov.core.feature_api.routes.SearchScreen
import com.egorshustov.search.api.SearchFeatureApi
import com.egorshustov.search.impl.main_search.MainSearchScreen
import com.egorshustov.search.impl.params_search.ParamsSearchScreen

class SearchFeatureImpl : SearchFeatureApi {

    private val searchGraphRoute = "search"

    override fun searchGraphRoute(): String = searchGraphRoute

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        navGraphBuilder.navigation(
            startDestination = SearchScreen.MAIN.screenRoute,
            route = searchGraphRoute
        ) {
            composable(
                route = SearchScreen.MAIN.screenRoute
            ) { navBackStackEntry ->
                MainSearchScreen()
            }
            composable(
                route = SearchScreen.PARAMS.screenRoute
            ) { navBackStackEntry ->
                ParamsSearchScreen()
            }
        }
    }
}