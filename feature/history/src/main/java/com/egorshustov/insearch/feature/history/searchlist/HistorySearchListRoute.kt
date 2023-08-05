package com.egorshustov.insearch.feature.history.searchlist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.egorshustov.insearch.core.model.data.SearchWithUsersPhotos
import com.egorshustov.insearch.feature.history.searchlist.components.HistorySearchListScreen

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