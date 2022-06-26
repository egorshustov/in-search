package com.egorshustov.vpoiske.feature.params.navigation

import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.egorshustov.vpoiske.core.navigation.AppNavigationDestination
import com.egorshustov.vpoiske.feature.params.ParamsSearchScreen
import com.egorshustov.vpoiske.feature.params.ParamsViewModel

object ParamsDestination : AppNavigationDestination {
    override val screenRoute = ParamsFeatureScreens.PARAMS.screenRoute
    override val graphDestination = "params_destination"
}

fun NavGraphBuilder.paramsGraph(
    modifier: Modifier
) {
    navigation(
        startDestination = ParamsDestination.screenRoute,
        route = ParamsDestination.graphDestination
    ) {
        composable(
            route = ParamsDestination.screenRoute
        ) {
            val viewModel = hiltViewModel<ParamsViewModel>()
            ParamsSearchScreen(
                state = viewModel.state.value,
                onTriggerEvent = viewModel::onTriggerEvent,
                modifier = modifier
            )
        }
    }
}