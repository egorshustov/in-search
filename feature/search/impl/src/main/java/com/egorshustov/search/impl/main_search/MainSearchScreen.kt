package com.egorshustov.search.impl.main_search

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
internal fun MainSearchScreen(
    state: MainSearchState,
    onTriggerEvent: (MainSearchEvent) -> Unit,
    onAuthRequired: () -> Unit,
    modifier: Modifier
) {
    if (state.isAuthRequired) onAuthRequired()
    Text(text = "MainSearchScreen")
}