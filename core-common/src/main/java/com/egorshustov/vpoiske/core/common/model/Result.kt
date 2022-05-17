package com.egorshustov.vpoiske.core.common.model

import com.egorshustov.vpoiske.core.common.model.Result.Success
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * A generic interface that holds a value with its loading status.
 * @param <T>
 */
sealed interface Result<out R> {
    data class Success<out T>(val data: T) : Result<T>
    data class Error(val exception: Throwable? = null) : Result<Nothing>
    object Loading : Result<Nothing>
}

/**
 * `true` if [Result] is of type [Success] & holds non-null [Success.data].
 */
val Result<*>.succeeded
    get() = this is Success && data != null

fun <T> Result<T>.successOr(fallback: T): T = (this as? Success<T>)?.data ?: fallback

val <T> Result<T>.data: T?
    get() = (this as? Success)?.data

/**
 * Updates value of [MutableStateFlow] if [Result] is of type [Success]
 */
inline fun <reified T> Result<T>.updateOnSuccess(stateFlow: MutableStateFlow<T>) {
    if (this is Success) stateFlow.value = data
}
