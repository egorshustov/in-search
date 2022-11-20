package com.egorshustov.insearch.feature.search.processsearch

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.work.HiltWorker
import androidx.work.*
import com.egorshustov.insearch.core.common.network.AppDispatchers.IO
import com.egorshustov.insearch.core.common.network.AppDispatchers.MAIN
import com.egorshustov.insearch.core.common.network.Dispatcher
import com.egorshustov.insearch.core.common.utils.NOTIFICATION_CHANNEL_ID
import com.egorshustov.insearch.core.common.utils.showMessage
import com.egorshustov.insearch.core.ui.R
import com.egorshustov.insearch.core.ui.api.UiMessage
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber

@HiltWorker
internal class ProcessSearchWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val presenterFactory: ProcessSearchPresenterImpl.Factory,
    @Dispatcher(MAIN) private val mainDispatcher: CoroutineDispatcher,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
) : CoroutineWorker(appContext, workerParams) {

    private val notificationBuilder =
        NotificationCompat.Builder(applicationContext, NOTIFICATION_CHANNEL_ID)

    private var presenter: ProcessSearchPresenter? = null

    override suspend fun doWork(): Result = withContext(ioDispatcher) {
        Timber.d("doWork. started")
        setForeground(createForegroundInfo())

        val completableJob = Job(currentCoroutineContext().job)
        presenter = presenterFactory.create(
            searchId = inputData.getLong(SEARCH_ID_ARG, 0),
            parentJob = completableJob
        ).also {
            launch(completableJob) { it.state.collectFlow() }
            launch(completableJob) { it.startSearch().join() }
        }
        completableJob.children.forEach { it.join() }

        Timber.d("doWork. completed")
        return@withContext Result.success()
    }

    private fun createForegroundInfo(): ForegroundInfo {
        val cancelWorkPendingIntent = WorkManager.getInstance(applicationContext)
            .createCancelPendingIntent(id)

        val titleText = applicationContext.getString(R.string.search_process_in_progress)
        val cancelText = applicationContext.getString(R.string.search_process_cancel)

        notificationBuilder
            .setContentTitle(titleText)
            .setContentIntent(createOpenActivityPendingIntent())
            .setSmallIcon(R.drawable.ic_search_24)
            .setOnlyAlertOnce(true)
            .setOngoing(true)
            .addAction(R.drawable.ic_stop_24, cancelText, cancelWorkPendingIntent)

        return ForegroundInfo(PROGRESS_NOTIFICATION_ID, notificationBuilder.build())
    }

    private fun createOpenActivityPendingIntent(): PendingIntent? = try {
        val activityClass = Class.forName("com.egorshustov.insearch.InSearchActivity")
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

    private suspend fun StateFlow<ProcessSearchState>.collectFlow(): Nothing = collect { state ->
        state.foundUsersLimit ?: return@collect
        state.message?.let { showMessage(it) }

        val processValue = state.foundUsersCount.toFloat() / state.foundUsersLimit
        setProgress(workDataOf(PROGRESS_VALUE_ARG to processValue))
        if (state.foundUsersCount >= state.foundUsersLimit) {
            sendCompleteNotification(state.foundUsersCount, state.foundUsersLimit)
        } else {
            sendProgressNotification(state.foundUsersCount, state.foundUsersLimit)
        }
    }

    private suspend fun showMessage(message: UiMessage) = withContext(mainDispatcher) {
        applicationContext.showMessage(message.getText(applicationContext))
        Timber.d("showMessage. message: $message")
        presenter?.onMessageShown(message.id)
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
            .setOngoing(false)
            .setProgress(0, 0, false)
            .mActions.clear()

        NotificationManagerCompat.from(applicationContext)
            .notify(COMPLETE_NOTIFICATION_ID, notificationBuilder.build())
    }

    companion object {
        const val SEARCH_ID_ARG = "SEARCH_ID_ARG"
        const val PROGRESS_VALUE_ARG = "PROGRESS_VALUE_ARG"

        private const val PROGRESS_NOTIFICATION_ID = 1
        private const val COMPLETE_NOTIFICATION_ID = 2
    }
}
