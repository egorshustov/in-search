package com.egorshustov.search.impl

import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.egorshustov.core.feature_api.routes.AuthScreen
import com.egorshustov.core.feature_api.routes.SearchScreen
import com.egorshustov.search.api.SearchFeatureApi
import com.egorshustov.search.impl.main_search.MainSearchScreen
import com.egorshustov.search.impl.main_search.MainSearchViewModel
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
            ) {
                val viewModel = hiltViewModel<MainSearchViewModel>()
                MainSearchScreen(
                    state = viewModel.state.value,
                    onTriggerEvent = viewModel::onTriggerEvent,
                    onAuthRequired = { navController.navigateToLoginAuthScreen() },
                    modifier = modifier
                )
            }
            composable(
                route = SearchScreen.PARAMS.screenRoute
            ) { navBackStackEntry ->
                ParamsSearchScreen()
            }
        }
    }

    private fun NavHostController.navigateToLoginAuthScreen() {
        navigate(AuthScreen.LOGIN.screenRoute) {
            launchSingleTop = true
            popUpTo(SearchScreen.MAIN.screenRoute) {
                inclusive = true
            }
        }
    }
}