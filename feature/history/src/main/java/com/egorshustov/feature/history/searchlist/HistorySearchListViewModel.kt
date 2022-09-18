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
import kotlinx.coroutines.flow.*
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
                    enablePlaceholders = true
                )
            )
        ).unwrapResult(loadingState, uiMessageManager)
            .cachedIn(viewModelScope)

    private val clickedSearchIdFlow = MutableStateFlow<Long?>(null)

    val state: StateFlow<HistorySearchListState> = combine(
        clickedSearchIdFlow,
        loadingState.flow,
        uiMessageManager.message
    ) { clickedSearchId, isLoading, message ->
        HistorySearchListState(
            clickedSearchId = clickedSearchId,
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
            is HistorySearchListEvent.OnClickSearchItem -> onClickSearchItem(event.searchId)
            HistorySearchListEvent.OnClickSearchItemHandled -> onClickSearchItemHandled()
            is HistorySearchListEvent.OnDismissSearchItem -> onDismissSearchItem(event.searchId)
            is HistorySearchListEvent.OnMessageShown -> onMessageShown(event.uiMessageId)
        }
    }

    private fun onClickSearchItem(searchId: Long) {
        clickedSearchIdFlow.update { searchId }
    }

    private fun onClickSearchItemHandled() {
        clickedSearchIdFlow.update { null }
    }

    private fun onDismissSearchItem(searchId: Long) {

    }

    private fun onMessageShown(uiMessageId: Long) {
        viewModelScope.launch {
            uiMessageManager.clearMessage(uiMessageId)
        }
    }
}