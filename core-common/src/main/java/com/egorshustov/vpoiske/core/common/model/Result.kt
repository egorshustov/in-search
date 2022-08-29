package com.egorshustov.vpoiske.core.common.model

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import timber.log.Timber

/**
 * A generic interface that holds a value with its loading status.
 * @param <T>
 */
sealed interface Result<out R> {
    data class Success<out T>(val data: T) : Result<T>
    data class Error(val exception: Throwable? = null) : Result<Nothing>
    object Loading : Result<Nothing>
}

fun <T> Flow<T>.asResult(): Flow<Result<T>> =
    map<T, Result<T>> { Result.Success(it) }
        .onStart { emit(Result.Loading) }
        .catch { emit(Result.Error(it)) }

suspend inline fun <T, R> Result<T>.map(crossinline transform: suspend (value: T) -> R): Result<R> =
    when (this) {
        is Result.Success ->
            try {
                Result.Success(transform(data))
            } catch (e: Throwable) {
                Timber.e(e)
                Result.Error(e)
            }
        is Result.Loading -> Result.Loading
        is Result.Error -> this
    }

inline fun <T, R> Flow<Result<T>>.mapResult(crossinline transform: suspend (value: T) -> R): Flow<Result<R>> =
    map { it.map(transform) }

inline fun <T> Flow<Result<T>>.filterResult(crossinline predicate: suspend (T) -> Boolean): Flow<Result<T>> =
    transform { value ->
        when (value) {
            is Result.Success -> if (predicate(value.data)) emit(value)
            else -> emit(value)
        }
    }

@OptIn(FlowPreview::class)
inline fun <T, R> Flow<Result<T>>.concatMapResult(crossinline transform: (value: T) -> Flow<Result<R>>): Flow<Result<R>> =
    flatMapConcat {
        when (it) {
            is Result.Success -> transform(it.data)
            is Result.Error -> flow { emit(it) }
            Result.Loading -> flow { emit(Result.Loading) }
        }
    }

inline fun <T> Flow<Result<T>>.doOnResultSuccess(crossinline action: suspend (value: T) -> Unit): Flow<Result<T>> =
    onEach { if (it is Result.Success) action(it.data) }

inline fun <T> Flow<Result<T>>.doOnResultError(crossinline action: suspend (value: Result.Error) -> Unit): Flow<Result<T>> =
    onEach { if (it is Result.Error) action(it) }

fun <T> Flow<Result<T>>.ignoreResultData(): Flow<Result<Unit>> =
    map { it.map { } }

/**
 * `true` if [Result] is of type [Result.Success] & holds non-null [Result.Success.data].
 */
val Result<*>.succeeded
    get() = this is Result.Success && data != null

fun <T> Result<T>.successOr(fallback: T): T = (this as? Result.Success<T>)?.data ?: fallback

val <T> Result<T>.data: T?
    get() = (this as? Result.Success)?.data

val <T> Result<T>.exception: Throwable?
    get() = (this as? Result.Error)?.exception

/**
 * Updates value of [MutableStateFlow] if [Result] is of type [Result.Success]
 */
inline fun <reified T> Result<T>.updateOnSuccess(stateFlow: MutableStateFlow<T>) {
    if (this is Result.Success) stateFlow.value = data
}
