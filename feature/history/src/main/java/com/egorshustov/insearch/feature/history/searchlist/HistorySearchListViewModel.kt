package com.egorshustov.insearch.feature.history.searchlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.egorshustov.insearch.core.domain.search.DeleteSearchUseCase
import com.egorshustov.insearch.core.domain.search.DeleteSearchUseCaseParams
import com.egorshustov.insearch.core.domain.search.GetSearchesWithUsersPhotosParams
import com.egorshustov.insearch.core.domain.search.GetSearchesWithUsersPhotosUseCase
import com.egorshustov.insearch.core.model.data.SearchWithUsersPhotos
import com.egorshustov.insearch.core.model.data.requestsparams.PagingConfigParams
import com.egorshustov.insearch.core.ui.api.UiMessageManager
import com.egorshustov.insearch.core.ui.util.ObservableLoadingCounter
import com.egorshustov.insearch.core.ui.util.WhileSubscribed
import com.egorshustov.insearch.core.ui.util.unwrapResult
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
    private val deleteSearchUseCase: DeleteSearchUseCase
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
            is HistorySearchListEvent.OnDismissSearchItem -> onDismissSearchItem(event.searchId)
            is HistorySearchListEvent.OnMessageShown -> onMessageShown(event.uiMessageId)
        }
    }

    private fun onDismissSearchItem(searchId: Long) {
        viewModelScope.launch {
            deleteSearchUseCase(DeleteSearchUseCaseParams(searchId))
        }
    }

    private fun onMessageShown(uiMessageId: Long) {
        viewModelScope.launch {
            uiMessageManager.clearMessage(uiMessageId)
        }
    }
}