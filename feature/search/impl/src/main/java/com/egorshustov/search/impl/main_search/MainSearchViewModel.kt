package com.egorshustov.search.impl.main_search

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkManager
import com.egorshustov.core.common.utils.DI_NAME_AUTH_ROUTE
import com.egorshustov.search.impl.SearchProcessWorker
import com.egorshustov.vpoiske.core.common.model.data
import com.egorshustov.vpoiske.core.domain.GetAccessTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
internal class MainSearchViewModel @Inject constructor(
    getAccessTokenUseCase: GetAccessTokenUseCase,
    @Named(DI_NAME_AUTH_ROUTE) val authRoute: String,
    @ApplicationContext appContext: Context
) : ViewModel() {

    private val _state: MutableState<MainSearchState> = mutableStateOf(MainSearchState())
    val state: State<MainSearchState> = _state

    init {
        viewModelScope.launch {
            getAccessTokenUseCase(Unit).collect {
                val accessToken = it.data
                _state.value = state.value.copy(isAuthRequired = accessToken.isNullOrBlank())
            }
        }
        enqueueWorkRequest(appContext)
    }

    private fun enqueueWorkRequest(appContext: Context) {
        val request = OneTimeWorkRequestBuilder<SearchProcessWorker>()
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .build()

        WorkManager.getInstance(appContext).enqueue(request)
    }

    fun onTriggerEvent(event: MainSearchEvent) {
        when (event) {
            MainSearchEvent.OnAuthRequested -> onAuthRequested()
        }
    }

    private fun onAuthRequested() {
        _state.value = state.value.copy(isAuthRequired = false)
    }
}