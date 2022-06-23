package com.egorshustov.vpoiske.feature.search.main_search

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
internal fun MainSearchScreen(
    state: MainSearchState,
    onTriggerEvent: (MainSearchEvent) -> Unit,
    requireAuth: () -> Unit,
    modifier: Modifier
) {
    if (state.isAuthRequired) {
        requireAuth()
        onTriggerEvent(MainSearchEvent.OnAuthRequested)
    }
    Text(text = "MainSearchScreen")
}