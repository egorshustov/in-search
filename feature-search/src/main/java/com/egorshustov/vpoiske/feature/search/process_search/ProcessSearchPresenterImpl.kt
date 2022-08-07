package com.egorshustov.vpoiske.feature.search.process_search

import com.egorshustov.vpoiske.core.common.network.AppDispatchers.IO
import com.egorshustov.vpoiske.core.common.network.Dispatcher
import com.egorshustov.vpoiske.core.ui.api.UiMessageManager
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject

internal class ProcessSearchPresenterImpl @Inject constructor(
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
) : ProcessSearchPresenter {

    // This exception handler allows to catch non-cancellation exceptions
    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        Timber.d("exceptionHandler thread ${Thread.currentThread().name}")
        Timber.d("Caught exceptionHandler $exception")
    }
    private val coroutineScope = CoroutineScope(ioDispatcher + exceptionHandler)

    private val uiMessageManager = UiMessageManager()
    private val foundUsersCountFlow: MutableSharedFlow<Int> = MutableSharedFlow(replay = 1)

    override val state: StateFlow<ProcessSearchState> =
        combine(
            foundUsersCountFlow,
            uiMessageManager.message,
        ) { foundUsersCount, message ->
            ProcessSearchState(
                foundUsersCount, message
            )
        }.stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ProcessSearchState.Empty
        )

    override suspend fun startSearch(searchId: Long): Job = coroutineScope.launch {
        Timber.d("ProcessSearch startSearch() at thread ${Thread.currentThread().name}")
        Timber.d("ProcessSearch startSearch() searchId $searchId")

        doDummyWork()
    }.apply {
        invokeOnCompletion {
            Timber.d("invokeOnCompletion thread ${Thread.currentThread().name}")
            Timber.d("Caught invokeOnCompletion $it")
        }
    }

    private suspend fun doDummyWork() {
        foundUsersCountFlow.emit(132)
        delay(1000)
        foundUsersCountFlow.emit(133)
        delay(5000)
        foundUsersCountFlow.emit(134)
        delay(1000)
        foundUsersCountFlow.emit(135)
        delay(1000)
    }

    override fun clearUiMessage(id: Long) {
        TODO("Not yet implemented")
    }
}