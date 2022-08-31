package com.egorshustov.vpoiske.feature.search.process_search

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.egorshustov.vpoiske.core.common.network.AppDispatchers.IO
import com.egorshustov.vpoiske.core.common.network.Dispatcher
import com.egorshustov.vpoiske.core.common.utils.NOTIFICATION_CHANNEL_ID
import com.egorshustov.vpoiske.core.ui.R
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.*
import timber.log.Timber

@HiltWorker
internal class ProcessSearchWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    presenterFactory: ProcessSearchPresenterImpl.Factory,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
) : CoroutineWorker(appContext, workerParams) {

    private val presenter: ProcessSearchPresenter =
        presenterFactory.create(inputData.getLong(SEARCH_ID_ARG, 0))

    private val notificationBuilder =
        NotificationCompat.Builder(applicationContext, NOTIFICATION_CHANNEL_ID)

    override suspend fun doWork(): Result = withContext(ioDispatcher) {
        setForeground(createForegroundInfo())

        val collectStateJob = CoroutineScope(coroutineContext).launch {
            collectProcessSearchState()
        }
        presenter.startSearch().join()
        collectStateJob.cancelAndJoin()

        Timber.d("Result.success()")
        return@withContext Result.success()
    }

    private fun createForegroundInfo(): ForegroundInfo {
        // This PendingIntent can be used to cancel the worker
        val cancelWorkPendingIntent = WorkManager.getInstance(applicationContext)
            .createCancelPendingIntent(id)

        val titleText = applicationContext.getString(R.string.search_process_in_progress)
        val cancelText = applicationContext.getString(R.string.search_process_cancel)

        val notification = NotificationCompat.Builder(applicationContext, NOTIFICATION_CHANNEL_ID)
            .setContentTitle(titleText)
            .setContentIntent(createOpenActivityPendingIntent())
            .setSmallIcon(R.drawable.ic_search_24)
            .setOnlyAlertOnce(true)
            .setOngoing(true)
            .addAction(R.drawable.ic_stop_24, cancelText, cancelWorkPendingIntent)
            .build()

        return ForegroundInfo(PROGRESS_NOTIFICATION_ID, notification)
    }

    private fun createOpenActivityPendingIntent(): PendingIntent? = try {
        val activityClass = Class.forName("com.egorshustov.vpoiske.VPoiskeActivity")
        val openActivityIntent = Intent(applicationContext, activityClass)
        PendingIntent.getActivity(
            applicationContext,
            0,
            openActivityIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    } catch (e: ClassNotFoundException) {
        null
    }

    private suspend fun collectProcessSearchState(): Nothing = presenter.state.collect {
        Timber.d(it.toString())
    }

    private fun sendProgressNotification(foundUsersCount: Int, foundUsersLimit: Int) {
        val contentText = applicationContext.getString(
            R.string.search_process_found,
            foundUsersCount,
            foundUsersLimit
        )
        notificationBuilder
            .setContentText(contentText)
            .setProgress(foundUsersLimit, foundUsersCount, false)

        NotificationManagerCompat.from(applicationContext)
            .notify(PROGRESS_NOTIFICATION_ID, notificationBuilder.build())
    }

    private fun sendCompleteNotification(foundUsersCount: Int, foundUsersLimit: Int) {
        val titleText = applicationContext.getString(R.string.search_process_completed)
        val contentText = applicationContext.getString(
            R.string.search_process_found,
            foundUsersCount,
            foundUsersLimit
        )
        notificationBuilder
            .setContentTitle(titleText)
            .setContentText(contentText)
            .setOnlyAlertOnce(false)
            .setAutoCancel(true)
            .setProgress(0, 0, false)
            .mActions.clear()

        NotificationManagerCompat.from(applicationContext)
            .notify(COMPLETE_NOTIFICATION_ID, notificationBuilder.build())
    }

    companion object {
        const val SEARCH_ID_ARG = "SEARCH_ID_ARG"

        private const val PROGRESS_NOTIFICATION_ID = 1
        private const val COMPLETE_NOTIFICATION_ID = 2
    }
}
