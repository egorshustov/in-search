package com.egorshustov.vpoiske.feature.search.main_search.components

import android.widget.Toast
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
    val context = LocalContext.current

    state.message?.let { message ->
        LaunchedEffect(message) {
            Toast.makeText(context, message.getText(context), Toast.LENGTH_LONG).show()
            // Notify the view model that the message has been dismissed
            onTriggerEvent(MainSearchEvent.OnMessageShown(message.id))
        }
    }

    if (state.users.isEmpty()) {
        NoSearchesStub(onStartNewSearchClick = onStartNewSearchClick)
    } else {
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