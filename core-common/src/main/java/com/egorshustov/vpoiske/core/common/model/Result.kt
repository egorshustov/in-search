package com.egorshustov.vpoiske.core.common.model

import kotlinx.coroutines.flow.*

/**
 * A generic interface that holds a value with its loading status.
 * @param <T>
 */
sealed interface Result<out R> {
    data class Success<out T>(val data: T) : Result<T>
    data class Error(val exception: AppException? = null) : Result<Nothing>
    object Loading : Result<Nothing>
}

fun <T> Flow<T>.asResult(): Flow<Result<T>> {
    return this
        .map<T, Result<T>> {
            Result.Success(it)
        }
        .onStart { emit(Result.Loading) }
        .catch { emit(Result.Error(AppException(it))) }
}

/**
 * `true` if [Result] is of type [Result.Success] & holds non-null [Result.Success.data].
 */
val Result<*>.succeeded
    get() = this is Result.Success && data != null

fun <T> Result<T>.successOr(fallback: T): T = (this as? Result.Success<T>)?.data ?: fallback

val <T> Result<T>.data: T?
    get() = (this as? Result.Success)?.data

/**
 * Updates value of [MutableStateFlow] if [Result] is of type [Result.Success]
 */
inline fun <reified T> Result<T>.updateOnSuccess(stateFlow: MutableStateFlow<T>) {
    if (this is Result.Success) stateFlow.value = data
}
