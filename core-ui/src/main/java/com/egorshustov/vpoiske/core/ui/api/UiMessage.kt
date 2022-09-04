package com.egorshustov.vpoiske.core.ui.api

import android.content.Context
import androidx.annotation.StringRes
import com.egorshustov.vpoiske.core.common.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.*

data class UiMessage(
    @StringRes private val messageResId: Int? = null,
    private val message: String? = null,
    val id: Long = UUID.randomUUID().mostSignificantBits
) {
    fun getText(context: Context): String = when {
        messageResId != null -> context.getString(messageResId)
        !message.isNullOrBlank() -> message
        else -> context.getString(R.string.error_unexpected)
    }
}

class UiMessageManager {
    private val mutex = Mutex()

    private val _messages = MutableStateFlow(emptyList<UiMessage>())

    /**
     * A flow emitting the current message to display.
     */
    val message: Flow<UiMessage?> = _messages.map { it.firstOrNull() }.distinctUntilChanged()

    suspend fun emitMessage(message: UiMessage) {
        mutex.withLock {
            _messages.value = _messages.value + message
        }
    }

    suspend fun clearMessage(id: Long) {
        mutex.withLock {
            _messages.value = _messages.value.filterNot { it.id == id }
        }
    }
}
