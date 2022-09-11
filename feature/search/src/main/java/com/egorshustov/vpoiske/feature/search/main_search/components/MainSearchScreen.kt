package com.egorshustov.vpoiske.feature.search.main_search.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import com.egorshustov.vpoiske.core.common.R
import com.egorshustov.vpoiske.core.model.data.mockUser
import com.egorshustov.vpoiske.core.ui.component.AppTopAppBar
import com.egorshustov.vpoiske.feature.search.main_search.MainSearchEvent
import com.egorshustov.vpoiske.feature.search.main_search.MainSearchState

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
        topBar = {
            Box(contentAlignment = Alignment.BottomCenter) {
                AppTopAppBar(
                    titleRes = R.string.app_name,
                    navigationIcon = Icons.Filled.Menu,
                    navigationIconContentDescriptionRes = R.string.app_open_drawer,
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
        if (state.users.isEmpty()) {
            NoSearchesStub(
                onStartNewSearchClick = onStartNewSearchClick,
                modifier = Modifier.padding(innerPadding)
            )
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = innerPadding
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