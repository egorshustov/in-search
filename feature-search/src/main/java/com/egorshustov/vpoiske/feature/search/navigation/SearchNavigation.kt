package com.egorshustov.vpoiske.feature.search.navigation

import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.egorshustov.vpoiske.core.navigation.AppNavigationDestination
import com.egorshustov.vpoiske.feature.search.main_search.MainSearchScreen
import com.egorshustov.vpoiske.feature.search.main_search.MainSearchViewModel

object SearchDestination : AppNavigationDestination {
    override val screenRoute = SearchFeatureScreens.MAIN.screenRoute
    override val graphDestination = "search_destination"
}

fun NavGraphBuilder.searchGraph(
    navigateToAuth: () -> Unit,
    navigateToParams: () -> Unit,
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
                onStartNewSearchClick = navigateToParams,
                modifier = modifier
            )
        }
    }
}