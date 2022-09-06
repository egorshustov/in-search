package com.egorshustov.vpoiske.core.ui.util

import com.egorshustov.vpoiske.core.common.R
import com.egorshustov.vpoiske.core.common.exceptions.DatabaseException
import com.egorshustov.vpoiske.core.common.exceptions.NetworkException
import com.egorshustov.vpoiske.core.common.exceptions.isFloodOrTooManyRequests
import com.egorshustov.vpoiske.core.common.model.Result
import com.egorshustov.vpoiske.core.ui.api.UiMessage
import com.egorshustov.vpoiske.core.ui.api.UiMessageManager
import kotlinx.coroutines.flow.*
import timber.log.Timber
import java.net.ConnectException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException
import java.util.concurrent.atomic.AtomicInteger

class ObservableLoadingCounter {
    private val count = AtomicInteger()
    private val loadingState = MutableStateFlow(count.get())

    val flow: Flow<Boolean>
        get() = loadingState.map { it > 0 }.distinctUntilChanged()

    fun addLoader() {
        loadingState.value = count.incrementAndGet()
    }

    fun removeLoader() {
        loadingState.value = count.decrementAndGet()
    }
}

fun <T> Flow<Result<T>>.unwrapResult(
    counter: ObservableLoadingCounter? = null,
    uiMessageManager: UiMessageManager? = null
): Flow<T> = transform { result ->
    when (result) {
        Result.Loading -> counter?.addLoader()
        is Result.Success -> {
            emit(result.data)
            counter?.removeLoader()
        }
        is Result.Error -> {
            Timber.w(result.exception)
            val uiMessage = result.exception.getUiMessage()
            if (uiMessageManager != null && uiMessage != null) {
                uiMessageManager.emitMessage(uiMessage)
            }
            counter?.removeLoader()
        }
    }
}

suspend fun <T> Result<T>.unwrapResult(
    counter: ObservableLoadingCounter? = null,
    uiMessageManager: UiMessageManager? = null
): T? = when (this) {
    Result.Loading -> {
        counter?.addLoader()
        null
    }
    is Result.Success -> {
        counter?.removeLoader()
        data
    }
    is Result.Error -> {
        Timber.w(exception)
        val uiMessage = exception.getUiMessage()
        if (uiMessageManager != null && uiMessage != null) {
            uiMessageManager.emitMessage(uiMessage)
        }
        counter?.removeLoader()
        null
    }
}

private fun Throwable?.getUiMessage(): UiMessage? = when {
    // Custom network exceptions:
    this?.isFloodOrTooManyRequests == true -> UiMessage(messageResId = R.string.error_network_flood)
    this is NetworkException.VkException -> UiMessage(message = message)
    // Custom database exceptions:
    this is DatabaseException.EntriesNotAddedException -> null
    this is DatabaseException.EntriesNotFoundException -> UiMessage(messageResId = R.string.error_database_entries_not_found)
    this is DatabaseException -> UiMessage(message = cause?.message)
    // Other exceptions:
    this is ConnectException || this is TimeoutException || this is UnknownHostException -> UiMessage(
        messageResId = R.string.error_network_check_internet_or_unavailable
    )
    else -> UiMessage(messageResId = R.string.error_something_went_wrong)
}
