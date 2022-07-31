package com.egorshustov.vpoiske.feature.search.main_search.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.egorshustov.vpoiske.feature.search.main_search.MainSearchEvent
import com.egorshustov.vpoiske.feature.search.main_search.MainSearchState

@Composable
internal fun MainSearchScreen(
    state: MainSearchState,
    onTriggerEvent: (MainSearchEvent) -> Unit,
    onStartNewSearchClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    if (state.users.isEmpty()) {
        NoSearchesStub(onStartNewSearchClick = onStartNewSearchClick)
    }
}

@Preview
@Composable
internal fun MainSearchScreenPreview() {
    MainSearchScreen(
        state = MainSearchState(),
        onTriggerEvent = {},
        onStartNewSearchClick = {},
        modifier = Modifier
    )
}