package com.egorshustov.vpoiske.core.domain.search

import com.egorshustov.vpoiske.core.common.network.AppDispatchers.IO
import com.egorshustov.vpoiske.core.common.network.Dispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

internal class ProcessSearchInteractorImpl @Inject constructor(
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
) : ProcessSearchInteractor {

    override suspend fun startSearch(searchId: Long) = withContext(ioDispatcher) {
        Timber.d("ProcessSearch startSearch() at thread ${Thread.currentThread().name}")
        Timber.d("ProcessSearch startSearch() searchId $searchId")
        delay(15000)
    }
}