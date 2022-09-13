package com.egorshustov.vpoiske.feature.search.main_search

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.annotation.StringRes
import androidx.lifecycle.*
import androidx.work.*
import com.egorshustov.vpoiske.core.common.R
import com.egorshustov.vpoiske.core.common.utils.MAX_COLUMN_COUNT
import com.egorshustov.vpoiske.core.common.utils.NO_VALUE_F
import com.egorshustov.vpoiske.core.common.utils.combine
import com.egorshustov.vpoiske.core.common.utils.log
import com.egorshustov.vpoiske.core.domain.column.GetColumnCountUseCase
import com.egorshustov.vpoiske.core.domain.column.SaveColumnCountUseCase
import com.egorshustov.vpoiske.core.domain.column.SaveColumnCountUseCaseParams
import com.egorshustov.vpoiske.core.domain.user.GetLastSearchUsersUseCase
import com.egorshustov.vpoiske.core.model.data.User
import com.egorshustov.vpoiske.core.ui.api.UiMessage
import com.egorshustov.vpoiske.core.ui.api.UiMessageManager
import com.egorshustov.vpoiske.core.ui.util.ObservableLoadingCounter
import com.egorshustov.vpoiske.core.ui.util.WhileSubscribed
import com.egorshustov.vpoiske.core.ui.util.unwrapResult
import com.egorshustov.vpoiske.feature.search.navigation.SearchDestination
import com.egorshustov.vpoiske.feature.search.process_search.ProcessSearchWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
internal class MainSearchViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getColumnCountUseCase: GetColumnCountUseCase,
    private val saveColumnCountUseCase: SaveColumnCountUseCase,
    getLastSearchUsersUseCase: GetLastSearchUsersUseCase,
    @ApplicationContext appContext: Context
) : ViewModel() {

    private val workManager = WorkManager.getInstance(appContext)

    private val liveWorkInfo: MediatorLiveData<WorkInfo?> = MediatorLiveData<WorkInfo?>()

    private val workInfoLiveObserver = Observer<List<WorkInfo>?> {
        Timber.e("Observer $it")
        liveWorkInfo.value = it.firstOrNull()
    }

    private val workInfoFlow: Flow<WorkInfo?> = liveWorkInfo.asFlow()

    init {
        liveWorkInfo.addSource(
            workManager.getWorkInfosForUniqueWorkLiveData(SEARCH_WORK_NAME),
            workInfoLiveObserver
        )
        workInfoFlow.onEach { // todo: remove after testing
            Timber.e(it.toString())
        }.launchIn(viewModelScope)
    }

    private val loadingState = ObservableLoadingCounter()
    private val uiMessageManager = UiMessageManager()

    private val columnCountFlow: Flow<Int> =
        getColumnCountUseCase(Unit).unwrapResult(loadingState, uiMessageManager)

    private val usersFlow: Flow<List<User>> =
        getLastSearchUsersUseCase(Unit).unwrapResult(loadingState, uiMessageManager)

    private val isSearchRunningFlow: StateFlow<Boolean> = workInfoFlow.map { workInfo ->
        workInfo?.state?.let { emitUiMessageIfSearchStateHasChanged(it) }
        workInfo?.state == WorkInfo.State.RUNNING
    }.stateIn(
        scope = viewModelScope,
        started = WhileSubscribed,
        initialValue = false
    )

    private suspend fun emitUiMessageIfSearchStateHasChanged(searchState: WorkInfo.State) {
        @StringRes val messageStrRes: Int? = when {
            !state.value.isSearchRunning && searchState == WorkInfo.State.RUNNING -> {
                R.string.search_process_is_running
            }
            state.value.isSearchRunning && searchState == WorkInfo.State.SUCCEEDED -> {
                R.string.search_process_completed
            }
            state.value.isSearchRunning && (searchState == WorkInfo.State.CANCELLED
                    || searchState == WorkInfo.State.FAILED) -> {
                R.string.search_process_cancelled
            }
            else -> null
        }
        messageStrRes?.let { uiMessageManager.emitMessage(UiMessage(it)) }
    }

    private val searchProcessValueFlow: StateFlow<Float> = workInfoFlow.transform { workInfo ->
        val searchProcessValue =
            workInfo?.progress?.getFloat(ProcessSearchWorker.PROGRESS_VALUE_ARG, NO_VALUE_F)
        if (searchProcessValue != NO_VALUE_F && searchProcessValue != null) {
            emit(searchProcessValue)
        }
    }.stateIn(
        scope = viewModelScope,
        started = WhileSubscribed,
        initialValue = 0f
    )

    private val isAuthRequiredFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)

    val state: StateFlow<MainSearchState> = combine(
        usersFlow,
        columnCountFlow,
        isSearchRunningFlow,
        searchProcessValueFlow,
        isAuthRequiredFlow,
        loadingState.flow,
        uiMessageManager.message
    ) { users,
        columnCount,
        isSearchRunning,
        searchProcessValue,
        isAuthRequired,
        isLoading,
        message ->

        MainSearchState(
            users = users,
            columnCount = columnCount,
            isSearchRunning = isSearchRunning,
            searchProcessValue = searchProcessValue,
            isAuthRequired = isAuthRequired,
            isLoading = isLoading,
            message = message
        )
    }.log("MainSearchState")
        .stateIn(
            scope = viewModelScope,
            started = WhileSubscribed,
            initialValue = MainSearchState.Empty
        )

    private val searchId: Long? = savedStateHandle.get<Long>(SearchDestination.searchIdArg)?.also {
        onTriggerEvent(MainSearchEvent.OnStartSearchProcess(it))
    }

    fun onTriggerEvent(event: MainSearchEvent) {
        when (event) {
            MainSearchEvent.OnAuthRequested -> onAuthRequested()
            is MainSearchEvent.OnStartSearchProcess -> onStartSearchProcess(
                searchId = event.searchId
            )
            MainSearchEvent.OnStopSearchProcess -> onStopSearchProcess()
            is MainSearchEvent.OnClickUserCard -> onClickUserCard(
                userId = event.userId, context = event.context
            )
            MainSearchEvent.OnChangeColumnCount -> onChangeColumnCount()
            is MainSearchEvent.OnMessageShown -> onMessageShown(event.uiMessageId)
        }
    }

    private fun onAuthRequested() {
        isAuthRequiredFlow.update { false }
    }

    private fun onStartSearchProcess(searchId: Long) {
        enqueueWorkRequest(searchId)
    }

    private fun enqueueWorkRequest(searchId: Long) {
        val data: Data = workDataOf(ProcessSearchWorker.SEARCH_ID_ARG to searchId)

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val request = OneTimeWorkRequestBuilder<ProcessSearchWorker>()
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .setInputData(data)
            .setConstraints(constraints)
            .build()

        workManager.enqueueUniqueWork(
            SEARCH_WORK_NAME,
            ExistingWorkPolicy.REPLACE,
            request
        )
    }

    private fun onStopSearchProcess() {
        workManager.cancelUniqueWork(SEARCH_WORK_NAME)
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

    companion object {
        private const val SEARCH_WORK_NAME = "ProcessSearchWorkName"
    }
}