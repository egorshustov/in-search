package com.egorshustov.vpoiske.core.common.utils

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber

fun <T> Flow<T>.log(
    title: String,
    priority: Int = Log.DEBUG
): Flow<T> = map { data ->
    data.also {
        Timber.log(priority, "$title: $it (at thread: $currentThreadName)")
    }
}