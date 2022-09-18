package com.egorshustov.feature.history.searchlist.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.egorshustov.feature.history.searchlist.HistorySearchListEvent
import com.egorshustov.feature.history.searchlist.HistorySearchListState
import com.egorshustov.vpoiske.core.common.R
import com.egorshustov.vpoiske.core.common.utils.NO_VALUE_L
import com.egorshustov.vpoiske.core.model.data.SearchWithUsersPhotos
import com.egorshustov.vpoiske.core.ui.component.AppTopAppBar
import com.egorshustov.vpoiske.core.ui.component.LoadingStub
import kotlinx.coroutines.flow.flowOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HistorySearchListScreen(
    state: HistorySearchListState,
    searchWithUsersPhotosPagingItems: LazyPagingItems<SearchWithUsersPhotos>,
    onTriggerEvent: (HistorySearchListEvent) -> Unit,
    onSearchItemClick: (searchId: Long) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    state.message?.let { message ->
        LaunchedEffect(message) {
            snackbarHostState.showSnackbar(message.getText(context))
            // Notify the view model that the message has been dismissed
            onTriggerEvent(HistorySearchListEvent.OnMessageShown(message.id))
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            AppTopAppBar(
                titleRes = R.string.history_search_history,
                navigationIcon = Icons.Filled.ArrowBack,
                navigationIconContentDescriptionRes = R.string.app_return_back,
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
        Box(contentAlignment = Alignment.Center) {
            LazyColumn(
                modifier = modifier,
                contentPadding = innerPadding
            ) {
                if (searchWithUsersPhotosPagingItems.loadState.refresh == LoadState.Loading) {
                    item { LoadingStub() }
                }

                items(
                    items = searchWithUsersPhotosPagingItems,
                    key = { it.search.id ?: NO_VALUE_L }
                ) { searchWithUsersPhotos ->
                    searchWithUsersPhotos?.let {
                        SearchCard(
                            search = it.search,
                            photos = it.photos,
                            onSearchCardClick = onSearchItemClick,
                            modifier = modifier
                        )
                    }
                }

                if (searchWithUsersPhotosPagingItems.loadState.append == LoadState.Loading) {
                    item {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth(Alignment.CenterHorizontally)
                        )
                    }
                }
            }
        }
    }
    if (state.isLoading) LoadingStub()
}

@Preview
@Composable
internal fun HistorySearchListScreenPreview() {
    HistorySearchListScreen(
        state = HistorySearchListState(),
        searchWithUsersPhotosPagingItems = flowOf(PagingData.from(emptyList<SearchWithUsersPhotos>()))
            .collectAsLazyPagingItems(),
        onTriggerEvent = {},
        onSearchItemClick = {},
        onBackClick = {}
    )
}