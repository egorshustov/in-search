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
    override val screenRoute = SearchScreen.PARAMS.screenRoute
    override val graphDestination = "search_destination"
}

fun NavGraphBuilder.searchGraph(
    navigateToAuth: () -> Unit,
    modifier: Modifier
) {
    navigation(
        startDestination = SearchScreen.MAIN.screenRoute,
        route = SearchDestination.graphDestination
    ) {
        composable(
            route = SearchScreen.MAIN.screenRoute
        ) {
            val viewModel = hiltViewModel<MainSearchViewModel>()
            MainSearchScreen(
                state = viewModel.state.value,
                onTriggerEvent = viewModel::onTriggerEvent,
                requireAuth = navigateToAuth,
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