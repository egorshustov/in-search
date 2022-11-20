package com.egorshustov.insearch.core.network

import com.egorshustov.insearch.core.common.exceptions.isFloodOrTooManyRequests
import com.egorshustov.insearch.core.common.model.Result
import io.ktor.client.statement.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.retryWhen
import timber.log.Timber
import kotlin.time.Duration

internal val HttpResponse.isSuccessful: Boolean
    get() = status.value in 200..299

internal fun <T> Flow<Result<T>>.retryWhenFloodError(
    delayDuration: Duration,
    retryAttemptsCount: Long
): Flow<Result<T>> = retryWhen { cause, attempt ->
    if (cause.isFloodOrTooManyRequests && attempt < retryAttemptsCount) {
        Timber.w("We're sending requests too frequently, let's wait for a ${delayDuration.inWholeSeconds} seconds")
        delay(delayDuration)
        Timber.w("Waited for a ${delayDuration.inWholeSeconds} seconds, let's resend request with $attempt attempt")
        true
    } else {
        Timber.w(cause)
        false
    }
}