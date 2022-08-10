package com.egorshustov.vpoiske.feature.search.main_search.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
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
    Column {
        if (state.users.isEmpty()) {
            NoSearchesStub(onStartNewSearchClick = onStartNewSearchClick)
        } else {
            state.users.forEach {
                Text(text = it.firstName + " " + it.lastName)
            }
        }
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