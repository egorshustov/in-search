package com.egorshustov.vpoiske.feature.search.main_search

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import androidx.work.*
import com.egorshustov.vpoiske.core.common.model.Result
import com.egorshustov.vpoiske.core.common.utils.NO_VALUE
import com.egorshustov.vpoiske.core.common.utils.currentThreadName
import com.egorshustov.vpoiske.core.domain.user.GetLastSearchUsersUseCase
import com.egorshustov.vpoiske.feature.search.navigation.SearchDestination
import com.egorshustov.vpoiske.feature.search.process_search.ProcessSearchWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
internal class MainSearchViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getLastSearchUsersUseCase: GetLastSearchUsersUseCase,
    @ApplicationContext appContext: Context
) : ViewModel() {

    private val liveSearchProcessPercentage: MediatorLiveData<Int> = MediatorLiveData<Int>()

    private val searchProcessPercentageFlow: Flow<Int> = liveSearchProcessPercentage.asFlow()

    private val workInfoLiveObserver = Observer<WorkInfo> { workInfo ->
        val searchProcessPercentage =
            workInfo.progress.getInt(ProcessSearchWorker.PROGRESS_PERCENTAGE_ARG, NO_VALUE)

        //Timber.e("workInfoLiveObserver $currentThreadName")
        if (searchProcessPercentage != NO_VALUE) {
            liveSearchProcessPercentage.value = searchProcessPercentage
        }
    }

    private val _state: MutableState<MainSearchState> = mutableStateOf(MainSearchState())
    val state: State<MainSearchState> = _state

    init {
        getLastSearchUsers()
        searchProcessPercentageFlow.onEach {
            Timber.e("$it $currentThreadName")
        }.launchIn(viewModelScope)

        //onStartSearchProcess(2, appContext) // TODO: remove after testing
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
        _state.value = state.value.copy(isAuthRequired = false)
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

    private fun getLastSearchUsers() {
        getLastSearchUsersUseCase(Unit).onEach {
            if (it is Result.Success) {
                _state.value = state.value.copy(users = it.data)
            }
        }.launchIn(viewModelScope)
    }
}