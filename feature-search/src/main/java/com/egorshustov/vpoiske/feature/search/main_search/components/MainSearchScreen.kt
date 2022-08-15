package com.egorshustov.vpoiske.feature.search.main_search.components

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.egorshustov.vpoiske.core.model.data.mockUser
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
    } else {
        val context = LocalContext.current
        LazyVerticalGrid(
            columns = GridCells.Fixed(3)
        ) {
            items(state.users) { user ->
                UserCard(
                    user = user,
                    onUserCardClick = { userId ->
                        onTriggerEvent(MainSearchEvent.OnClickUserCard(userId, context))
                    }
                )
            }
        }
    }
}

@Preview
@Composable
internal fun MainSearchScreenPreview() {
    MainSearchScreen(
        state = MainSearchState(users = List(100) { mockUser }),
        onTriggerEvent = {},
        onStartNewSearchClick = {},
        modifier = Modifier
    )
}