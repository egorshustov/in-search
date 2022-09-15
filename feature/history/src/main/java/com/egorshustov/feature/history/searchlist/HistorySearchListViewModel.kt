package com.egorshustov.feature.history.searchlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.egorshustov.vpoiske.core.domain.search.DeleteSearchUseCase
import com.egorshustov.vpoiske.core.domain.search.GetSearchesWithUsersPhotosParams
import com.egorshustov.vpoiske.core.domain.search.GetSearchesWithUsersPhotosUseCase
import com.egorshustov.vpoiske.core.model.data.SearchWithUsersPhotos
import com.egorshustov.vpoiske.core.model.data.requestsparams.PagingConfigParams
import com.egorshustov.vpoiske.core.ui.api.UiMessageManager
import com.egorshustov.vpoiske.core.ui.util.ObservableLoadingCounter
import com.egorshustov.vpoiske.core.ui.util.WhileSubscribed
import com.egorshustov.vpoiske.core.ui.util.unwrapResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class HistorySearchListViewModel @Inject constructor(
    getSearchesWithUsersPhotosUseCase: GetSearchesWithUsersPhotosUseCase,
    deleteSearchUseCase: DeleteSearchUseCase
) : ViewModel() {

    private val loadingState = ObservableLoadingCounter()
    private val uiMessageManager = UiMessageManager()

    val searchWithUsersPhotosPagedFlow: Flow<PagingData<SearchWithUsersPhotos>> =
        getSearchesWithUsersPhotosUseCase(
            GetSearchesWithUsersPhotosParams(
                PagingConfigParams(
                    pageSize = 10,
                    maxSize = 30,
                    enablePlaceholders = false
                )
            )
        ).unwrapResult(loadingState, uiMessageManager)
            .cachedIn(viewModelScope)


    val state: StateFlow<HistorySearchListState> = combine(
        loadingState.flow,
        uiMessageManager.message
    ) { isLoading, message ->
        HistorySearchListState(
            isLoading = isLoading,
            message = message
        )
    }.stateIn(
        scope = viewModelScope,
        started = WhileSubscribed,
        initialValue = HistorySearchListState.Default
    )

    fun onTriggerEvent(event: HistorySearchListEvent) {
        when (event) {
            is HistorySearchListEvent.OnClickSearch -> onClickSearch()
            is HistorySearchListEvent.OnDismissSearch -> onDismissSearch()
            is HistorySearchListEvent.OnMessageShown -> onMessageShown(event.uiMessageId)
        }
    }

    private fun onClickSearch() {

    }

    private fun onDismissSearch() {

    }

    private fun onMessageShown(uiMessageId: Long) {
        viewModelScope.launch {
            uiMessageManager.clearMessage(uiMessageId)
        }
    }
}