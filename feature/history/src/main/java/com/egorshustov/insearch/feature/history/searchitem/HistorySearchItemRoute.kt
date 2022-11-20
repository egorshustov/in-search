package com.egorshustov.insearch.feature.history.searchitem

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.egorshustov.insearch.feature.history.searchitem.components.HistorySearchItemScreen

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
internal fun HistorySearchItemRoute(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    viewModel: HistorySearchItemViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val onTriggerEvent = viewModel::onTriggerEvent

    HistorySearchItemScreen(
        state = state,
        onTriggerEvent = onTriggerEvent,
        onBackClick = onBackClick,
        modifier = modifier
    )
}