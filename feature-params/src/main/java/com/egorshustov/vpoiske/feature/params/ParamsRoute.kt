package com.egorshustov.vpoiske.feature.params

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.egorshustov.vpoiske.feature.params.components.ParamsScreen

@Composable
internal fun ParamsRoute(
    modifier: Modifier = Modifier,
    startSearchProcess: (searchId: Long) -> Unit,
    requireAuth: () -> Unit,
    viewModel: ParamsViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val onTriggerEvent = viewModel::onTriggerEvent

    if (state.searchState.searchId != null) {
        startSearchProcess(state.searchState.searchId)
        onTriggerEvent(ParamsEvent.OnSearchProcessInitiated)
    }

    if (state.authState.isAuthRequired) {
        requireAuth()
        onTriggerEvent(ParamsEvent.OnAuthRequested)
    }

    ParamsScreen(
        state = state,
        onTriggerEvent = onTriggerEvent,
        modifier = modifier
    )
}