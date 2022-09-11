package com.egorshustov.vpoiske.core.common.model

/**
 * A generic interface that holds a value with its loading status.
 * @param <T>
 */
sealed interface Result<out R> {
    data class Success<out T>(val data: T) : Result<T>
    data class Error(val exception: Throwable? = null) : Result<Nothing>
    object Loading : Result<Nothing>
}
