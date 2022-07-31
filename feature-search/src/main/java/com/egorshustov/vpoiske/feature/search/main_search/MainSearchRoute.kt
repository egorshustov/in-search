package com.egorshustov.vpoiske.feature.search.main_search

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.egorshustov.vpoiske.feature.search.main_search.components.MainSearchScreen

@Composable
internal fun MainSearchRoute(
    modifier: Modifier = Modifier,
    requireAuth: () -> Unit,
    onStartNewSearchClick: () -> Unit,
    viewModel: MainSearchViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val onTriggerEvent = viewModel::onTriggerEvent

    if (state.isAuthRequired) {
        requireAuth()
        onTriggerEvent(MainSearchEvent.OnAuthRequested)
    }

    MainSearchScreen(
        state = state,
        onTriggerEvent = onTriggerEvent,
        onStartNewSearchClick = onStartNewSearchClick,
        modifier = modifier
    )
}