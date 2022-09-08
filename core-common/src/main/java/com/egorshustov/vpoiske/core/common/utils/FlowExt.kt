package com.egorshustov.vpoiske.core.common.utils

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

fun <T> Flow<T>.log(
    title: String,
    priority: Int = Log.DEBUG
): Flow<T> = onEach { value ->
    Timber.log(priority, "$title: $value (at thread: $currentThreadName)")
}

inline fun <T> Flow<List<T>>.filterList(crossinline predicate: (T) -> Boolean): Flow<List<T>> =
    map { it.filter(predicate) }