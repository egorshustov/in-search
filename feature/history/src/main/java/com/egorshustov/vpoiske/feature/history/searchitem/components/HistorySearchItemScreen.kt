package com.egorshustov.vpoiske.feature.history.searchitem.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.egorshustov.vpoiske.core.common.R
import com.egorshustov.vpoiske.core.model.data.mockUser
import com.egorshustov.vpoiske.core.ui.component.AppTopAppBar
import com.egorshustov.vpoiske.core.ui.component.ChangeColumnCountIconButton
import com.egorshustov.vpoiske.core.ui.component.LoadingStub
import com.egorshustov.vpoiske.core.ui.component.UsersGrid
import com.egorshustov.vpoiske.feature.history.searchitem.HistorySearchItemEvent
import com.egorshustov.vpoiske.feature.history.searchitem.HistorySearchItemState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HistorySearchItemScreen(
    state: HistorySearchItemState,
    onTriggerEvent: (HistorySearchItemEvent) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    state.message?.let { message ->
        LaunchedEffect(message) {
            snackbarHostState.showSnackbar(message.getText(context))
            // Notify the view model that the message has been dismissed
            onTriggerEvent(HistorySearchItemEvent.OnMessageShown(message.id))
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            AppTopAppBar(
                titleRes = R.string.history_search_users,
                navigationIcon = Icons.Filled.ArrowBack,
                navigationIconContentDescriptionRes = com.egorshustov.vpoiske.core.common.R.string.app_return_back,
                actions = {
                    if (state.users.isNotEmpty()) {
                        ChangeColumnCountIconButton(onChangeColumnCountClick = {
                            onTriggerEvent(HistorySearchItemEvent.OnChangeColumnCount)
                        })
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                ),
                modifier = Modifier.windowInsetsPadding(
                    WindowInsets.safeDrawing.only(WindowInsetsSides.Top)
                ),
                onNavigationClick = onBackClick
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        when {
            state.isLoading -> LoadingStub()
            state.users.isEmpty() -> NoUsersStub(modifier = Modifier.padding(innerPadding))
            else -> UsersGrid(
                users = state.users,
                columnCount = state.columnCount,
                onUserCardClick = { userId ->
                    onTriggerEvent(HistorySearchItemEvent.OnClickUserCard(userId, context))
                },
                contentPadding = innerPadding
            )
        }
    }
}

@Preview
@Composable
internal fun HistorySearchItemScreenPreview() {
    HistorySearchItemScreen(
        state = HistorySearchItemState(users = List(100) { mockUser }),
        onTriggerEvent = {},
        onBackClick = {}
    )
}