package com.egorshustov.vpoiske.feature.search.process_search

import kotlinx.coroutines.delay
import timber.log.Timber
import javax.inject.Inject

internal class ProcessSearchInteractor @Inject constructor() {

    suspend fun onWorkStarted(searchId: Long) {
        Timber.d("doWork() at thread ${Thread.currentThread().name}")
        Timber.d("doWork() searchId $searchId")
        delay(15000)
    }
}