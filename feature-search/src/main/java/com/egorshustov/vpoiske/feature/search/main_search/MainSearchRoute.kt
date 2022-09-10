package com.egorshustov.vpoiske.feature.search.main_search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.egorshustov.vpoiske.feature.search.main_search.components.MainSearchScreen

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
internal fun MainSearchRoute(
    modifier: Modifier = Modifier,
    requireAuth: () -> Unit,
    onStartNewSearchClick: () -> Unit,
    openDrawer: () -> Unit,
    viewModel: MainSearchViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val onTriggerEvent = viewModel::onTriggerEvent

    if (state.isAuthRequired) {
        requireAuth()
        onTriggerEvent(MainSearchEvent.OnAuthRequested)
    }

    MainSearchScreen(
        state = state,
        onTriggerEvent = onTriggerEvent,
        onStartNewSearchClick = onStartNewSearchClick,
        openDrawer = openDrawer,
        modifier = modifier
    )
}