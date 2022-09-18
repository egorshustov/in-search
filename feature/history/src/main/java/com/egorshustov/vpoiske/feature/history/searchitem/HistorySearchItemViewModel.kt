package com.egorshustov.vpoiske.feature.history.searchitem

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.egorshustov.vpoiske.core.common.utils.MAX_COLUMN_COUNT
import com.egorshustov.vpoiske.core.domain.column.GetColumnCountUseCase
import com.egorshustov.vpoiske.core.domain.column.SaveColumnCountUseCase
import com.egorshustov.vpoiske.core.domain.column.SaveColumnCountUseCaseParams
import com.egorshustov.vpoiske.core.domain.user.GetSearchUsersUseCase
import com.egorshustov.vpoiske.core.domain.user.GetSearchUsersUseCaseParams
import com.egorshustov.vpoiske.core.model.data.User
import com.egorshustov.vpoiske.core.ui.api.UiMessageManager
import com.egorshustov.vpoiske.core.ui.util.ObservableLoadingCounter
import com.egorshustov.vpoiske.core.ui.util.WhileSubscribed
import com.egorshustov.vpoiske.core.ui.util.unwrapResult
import com.egorshustov.vpoiske.feature.history.navigation.HistoryDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class HistorySearchItemViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getColumnCountUseCase: GetColumnCountUseCase,
    private val saveColumnCountUseCase: SaveColumnCountUseCase,
    getSearchUsersUseCase: GetSearchUsersUseCase
) : ViewModel() {

    private val searchId: Long = checkNotNull(savedStateHandle[HistoryDestination.searchIdArg])

    private val loadingState = ObservableLoadingCounter()
    private val uiMessageManager = UiMessageManager()

    private val columnCountFlow: Flow<Int> =
        getColumnCountUseCase(Unit).unwrapResult(loadingState, uiMessageManager)

    private val usersFlow: Flow<List<User>> =
        getSearchUsersUseCase(GetSearchUsersUseCaseParams(searchId)).unwrapResult(
            loadingState,
            uiMessageManager
        )

    val state: StateFlow<HistorySearchItemState> = combine(
        usersFlow,
        columnCountFlow,
        loadingState.flow,
        uiMessageManager.message
    ) { users, columnCount, isLoading, message ->
        HistorySearchItemState(
            users = users,
            columnCount = columnCount,
            isLoading = isLoading,
            message = message
        )
    }.stateIn(
        scope = viewModelScope,
        started = WhileSubscribed,
        initialValue = HistorySearchItemState.Default
    )

    fun onTriggerEvent(event: HistorySearchItemEvent) {
        when (event) {
            is HistorySearchItemEvent.OnClickUserCard -> onClickUserCard(
                userId = event.userId, context = event.context
            )
            HistorySearchItemEvent.OnChangeColumnCount -> onChangeColumnCount()
            is HistorySearchItemEvent.OnMessageShown -> onMessageShown(event.uiMessageId)
        }
    }

    private fun onClickUserCard(userId: Long, context: Context) {
        val userUrl = "https://vk.com/id$userId"
        val intent = Intent(Intent.ACTION_VIEW).apply { data = Uri.parse(userUrl) }
        context.startActivity(intent)
    }

    private fun onChangeColumnCount() {
        viewModelScope.launch {
            val newColumnCount =
                if (state.value.columnCount.dec() == 0) MAX_COLUMN_COUNT else state.value.columnCount.dec()
            saveColumnCountUseCase(SaveColumnCountUseCaseParams(newColumnCount))
        }
    }

    private fun onMessageShown(uiMessageId: Long) {
        viewModelScope.launch {
            uiMessageManager.clearMessage(uiMessageId)
        }
    }
}