package com.egorshustov.vpoiske.feature.search.process_search

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import timber.log.Timber

@HiltWorker
internal class ProcessSearchWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        val searchId = inputData.getLong(SEARCH_ID_ARG, 0)

        Timber.d("doWork() at thread ${Thread.currentThread().name}")
        Timber.d("doWork() searchId $searchId")
        return Result.success()
    }

    companion object {
        const val SEARCH_ID_ARG = "SEARCH_ID_ARG"
    }
}
