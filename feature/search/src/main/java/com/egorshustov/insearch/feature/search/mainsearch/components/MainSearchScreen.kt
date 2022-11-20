package com.egorshustov.insearch.feature.search.mainsearch.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import com.egorshustov.insearch.core.model.data.mockUser
import com.egorshustov.insearch.core.ui.R
import com.egorshustov.insearch.core.ui.component.AppTopAppBar
import com.egorshustov.insearch.core.ui.component.ChangeColumnCountIconButton
import com.egorshustov.insearch.core.ui.component.LoadingStub
import com.egorshustov.insearch.core.ui.component.UsersGrid
import com.egorshustov.insearch.feature.search.mainsearch.MainSearchEvent
import com.egorshustov.insearch.feature.search.mainsearch.MainSearchState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MainSearchScreen(
    state: MainSearchState,
    onTriggerEvent: (MainSearchEvent) -> Unit,
    onStartNewSearchClick: () -> Unit,
    openDrawer: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    state.message?.let { message ->
        LaunchedEffect(message) {
            snackbarHostState.showSnackbar(message.getText(context))
            // Notify the view model that the message has been dismissed
            onTriggerEvent(MainSearchEvent.OnMessageShown(message.id))
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            Box(contentAlignment = Alignment.BottomCenter) {
                AppTopAppBar(
                    titleRes = R.string.app_name,
                    navigationIcon = Icons.Filled.Menu,
                    navigationIconContentDescriptionRes = R.string.app_open_drawer,
                    actions = {
                        if (state.isSearchRunning) {
                            StopSearchButton(onStopSearchClick = {
                                onTriggerEvent(MainSearchEvent.OnStopSearchProcess)
                            })
                        }
                        if (state.users.isNotEmpty()) {
                            ChangeColumnCountIconButton(onChangeColumnCountClick = {
                                onTriggerEvent(MainSearchEvent.OnChangeColumnCount)
                            })
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color.Transparent
                    ),
                    modifier = Modifier.windowInsetsPadding(
                        WindowInsets.safeDrawing.only(WindowInsetsSides.Top)
                    ),
                    onNavigationClick = openDrawer
                )
                if (state.isSearchRunning) {
                    val progressValue by remember(state.searchProcessValue) {
                        mutableStateOf(state.searchProcessValue)
                    }
                    val animatedProgress by animateFloatAsState(
                        targetValue = progressValue,
                        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
                    )

                    LinearProgressIndicator(
                        modifier = Modifier
                            .semantics(mergeDescendants = true) {}
                            .fillMaxWidth(),
                        progress = animatedProgress
                    )
                }
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        when {
            state.isLoading || (state.isSearchRunning && state.users.isEmpty()) -> LoadingStub()
            state.users.isEmpty() -> NoUsersStub(
                onStartNewSearchClick = onStartNewSearchClick,
                modifier = Modifier.padding(innerPadding)
            )
            else -> UsersGrid(
                users = state.users,
                columnCount = state.columnCount,
                onUserCardClick = { userId ->
                    onTriggerEvent(MainSearchEvent.OnClickUserCard(userId, context))
                },
                contentPadding = innerPadding
            )
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
        openDrawer = {},
        modifier = Modifier
    )
}