package com.egorshustov.vpoiske.feature.search.navigation

import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.egorshustov.vpoiske.core.navigation.AppNavigationDestination
import com.egorshustov.vpoiske.feature.search.main_search.MainSearchScreen
import com.egorshustov.vpoiske.feature.search.main_search.MainSearchViewModel
import com.egorshustov.vpoiske.feature.search.params_search.ParamsSearchScreen

object SearchDestination : AppNavigationDestination {
    override val screenRoute = SearchFeatureScreens.PARAMS.screenRoute
    override val graphDestination = "search_destination"
}

fun NavGraphBuilder.searchGraph(
    navigateToAuth: () -> Unit,
    navigateToSearchParams: () -> Unit,
    modifier: Modifier
) {
    navigation(
        startDestination = SearchFeatureScreens.MAIN.screenRoute,
        route = SearchDestination.graphDestination
    ) {
        composable(
            route = SearchFeatureScreens.MAIN.screenRoute
        ) {
            val viewModel = hiltViewModel<MainSearchViewModel>()
            MainSearchScreen(
                state = viewModel.state.value,
                onTriggerEvent = viewModel::onTriggerEvent,
                requireAuth = navigateToAuth,
                onStartNewSearchClick = navigateToSearchParams,
                modifier = modifier
            )
        }
        composable(
            route = SearchDestination.screenRoute
        ) { navBackStackEntry ->
            ParamsSearchScreen()
        }
    }
}