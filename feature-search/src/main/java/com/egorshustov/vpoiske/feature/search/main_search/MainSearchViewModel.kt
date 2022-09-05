package com.egorshustov.vpoiske.feature.search.main_search

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.*
import androidx.work.*
import com.egorshustov.vpoiske.core.common.utils.NO_VALUE
import com.egorshustov.vpoiske.core.common.utils.log
import com.egorshustov.vpoiske.core.domain.user.GetLastSearchUsersUseCase
import com.egorshustov.vpoiske.core.model.data.User
import com.egorshustov.vpoiske.core.ui.api.UiMessageManager
import com.egorshustov.vpoiske.core.ui.util.ObservableLoadingCounter
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
    getLastSearchUsersUseCase: GetLastSearchUsersUseCase,
    @ApplicationContext appContext: Context
) : ViewModel() {

    private val liveSearchProcessPercentage: MediatorLiveData<Int> = MediatorLiveData<Int>()

    private val workInfoLiveObserver = Observer<WorkInfo> { workInfo ->
        val searchProcessPercentage =
            workInfo.progress.getInt(ProcessSearchWorker.PROGRESS_PERCENTAGE_ARG, NO_VALUE)

        //Timber.e("workInfoLiveObserver $currentThreadName")
        if (searchProcessPercentage != NO_VALUE) {
            liveSearchProcessPercentage.value = searchProcessPercentage
        }
    }

    private val loadingState = ObservableLoadingCounter()
    private val uiMessageManager = UiMessageManager()

    private val usersFlow: Flow<List<User>> =
        getLastSearchUsersUseCase(Unit).unwrapResult(loadingState, uiMessageManager)

    private val searchProcessPercentageFlow: Flow<Int?> = liveSearchProcessPercentage.asFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    private val isAuthRequiredFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)

    val state: StateFlow<MainSearchState> = combine(
        usersFlow,
        searchProcessPercentageFlow,
        isAuthRequiredFlow,
        loadingState.flow,
        uiMessageManager.message,
    ) { users, searchProcessPercentage, isAuthRequired, isLoading, message ->
        MainSearchState(users, searchProcessPercentage, isAuthRequired, isLoading, message)
    }.log("MainSearchState")
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = MainSearchState.Empty
        )

    init {
        viewModelScope.launch {
            usersFlow.collect {
                Timber.e(it.toString())
            }
        }
    }

    private val searchId: Long? = savedStateHandle.get<Long>(SearchDestination.searchIdArg)?.also {
        onTriggerEvent(MainSearchEvent.OnStartSearchProcess(it, appContext))
    }

    fun onTriggerEvent(event: MainSearchEvent) {
        when (event) {
            MainSearchEvent.OnAuthRequested -> onAuthRequested()
            is MainSearchEvent.OnStartSearchProcess -> onStartSearchProcess(
                searchId = event.searchId, appContext = event.appContext
            )
            is MainSearchEvent.OnClickUserCard -> onClickUserCard(
                userId = event.userId, context = event.context
            )
        }
    }

    private fun onAuthRequested() {
        isAuthRequiredFlow.value = false
    }

    private fun onStartSearchProcess(searchId: Long, appContext: Context) {
        enqueueWorkRequest(searchId, appContext)
    }

    private fun onClickUserCard(userId: Long, context: Context) {
        val userUrl = "https://vk.com/id$userId"
        val intent = Intent(Intent.ACTION_VIEW).apply { data = Uri.parse(userUrl) }
        context.startActivity(intent)
    }

    private fun enqueueWorkRequest(searchId: Long, appContext: Context) {
        val data: Data = workDataOf(ProcessSearchWorker.SEARCH_ID_ARG to searchId)

        val request = OneTimeWorkRequestBuilder<ProcessSearchWorker>()
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .setInputData(data)
            .build()

        val workManager = WorkManager.getInstance(appContext)
        workManager.enqueueUniqueWork(
            "ProcessSearchWorker",
            ExistingWorkPolicy.REPLACE, // todo replace with KEEP after testing
            request
        )
        liveSearchProcessPercentage.addSource(
            workManager.getWorkInfoByIdLiveData(request.id),
            workInfoLiveObserver
        )
    }
}