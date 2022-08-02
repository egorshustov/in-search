package com.egorshustov.vpoiske.feature.search.main_search

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.*
import com.egorshustov.vpoiske.core.common.model.Result
import com.egorshustov.vpoiske.core.domain.user.GetLastSearchUsersUseCase
import com.egorshustov.vpoiske.feature.search.navigation.SearchDestination
import com.egorshustov.vpoiske.feature.search.process_search.ProcessSearchWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
internal class MainSearchViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    val getLastSearchUsersUseCase: GetLastSearchUsersUseCase,
    @ApplicationContext appContext: Context
) : ViewModel() {

    private val searchId: Long? = savedStateHandle.get<Long>(SearchDestination.searchIdArg)?.also {
        onTriggerEvent(MainSearchEvent.OnStartSearchProcess(it, appContext))
    }

    private val _state: MutableState<MainSearchState> = mutableStateOf(MainSearchState())
    val state: State<MainSearchState> = _state

    init {
        getLastSearchUsers()
    }

    fun onTriggerEvent(event: MainSearchEvent) {
        when (event) {
            MainSearchEvent.OnAuthRequested -> onAuthRequested()
            is MainSearchEvent.OnStartSearchProcess -> onStartSearchProcess(
                searchId = event.searchId, appContext = event.appContext
            )
        }
    }

    private fun onAuthRequested() {
        _state.value = state.value.copy(isAuthRequired = false)
    }

    private fun onStartSearchProcess(searchId: Long, appContext: Context) {
        enqueueWorkRequest(searchId, appContext)
    }

    private fun enqueueWorkRequest(searchId: Long, appContext: Context) {
        val data: Data = workDataOf(ProcessSearchWorker.SEARCH_ID_ARG to searchId)

        val request = OneTimeWorkRequestBuilder<ProcessSearchWorker>()
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .setInputData(data)
            .build()

        WorkManager.getInstance(appContext).enqueue(request)
    }

    private fun getLastSearchUsers() {
        getLastSearchUsersUseCase(Unit).onEach {
            if (it is Result.Success) {
                _state.value = state.value.copy(users = it.data)
            }
        }.launchIn(viewModelScope)
    }
}