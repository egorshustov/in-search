package com.egorshustov.insearch.feature.params

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.egorshustov.insearch.feature.params.components.ParamsScreen

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
internal fun ParamsRoute(
    modifier: Modifier = Modifier,
    startSearchProcess: (searchId: Long) -> Unit,
    requireAuth: () -> Unit,
    onBackClick: () -> Unit,
    viewModel: ParamsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val onTriggerEvent = viewModel::onTriggerEvent

    state.searchState.searchId?.let {
        startSearchProcess(it)
        onTriggerEvent(ParamsEvent.OnSearchProcessInitiated)
    }

    if (state.authState.isAuthRequired) {
        requireAuth()
        onTriggerEvent(ParamsEvent.OnAuthRequested)
    }

    ParamsScreen(
        state = state,
        onTriggerEvent = onTriggerEvent,
        onBackClick = onBackClick,
        modifier = modifier
    )
}