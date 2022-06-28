package com.egorshustov.vpoiske.feature.params

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.egorshustov.vpoiske.feature.params.components.ParamsScreen

@Composable
internal fun ParamsRoute(
    modifier: Modifier = Modifier,
    viewModel: ParamsViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val onTriggerEvent = viewModel::onTriggerEvent

    ParamsScreen(
        state = state,
        onTriggerEvent = onTriggerEvent,
        modifier = modifier
    )
}