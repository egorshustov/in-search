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
    override val route = "params_route"
    override val destination = "params_destination"
}

fun NavGraphBuilder.paramsGraph(
    modifier: Modifier
) {
    navigation(
        route = ParamsDestination.route,
        startDestination = ParamsDestination.destination
    ) {
        composable(route = ParamsDestination.destination) {
            val viewModel: ParamsViewModel = hiltViewModel()
            ParamsSearchScreen(
                state = viewModel.state.value,
                onTriggerEvent = viewModel::onTriggerEvent,
                modifier = modifier
            )
        }
    }
}