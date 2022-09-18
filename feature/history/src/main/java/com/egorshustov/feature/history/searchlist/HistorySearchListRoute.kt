package com.egorshustov.feature.history.searchlist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.egorshustov.feature.history.searchlist.components.HistorySearchListScreen
import com.egorshustov.vpoiske.core.model.data.SearchWithUsersPhotos

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
internal fun HistorySearchListRoute(
    modifier: Modifier = Modifier,
    onSearchItemClick: (searchId: Long) -> Unit,
    onBackClick: () -> Unit,
    viewModel: HistorySearchListViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val searchWithUsersPhotosPagingItems: LazyPagingItems<SearchWithUsersPhotos> =
        viewModel.searchWithUsersPhotosPagedFlow.collectAsLazyPagingItems()

    val onTriggerEvent = viewModel::onTriggerEvent

    HistorySearchListScreen(
        state = state,
        searchWithUsersPhotosPagingItems = searchWithUsersPhotosPagingItems,
        onTriggerEvent = onTriggerEvent,
        onSearchItemClick = onSearchItemClick,
        onBackClick = onBackClick,
        modifier = modifier
    )
}