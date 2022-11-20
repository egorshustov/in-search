package com.egorshustov.insearch.core.ui.api

import android.content.Context
import androidx.annotation.StringRes
import com.egorshustov.insearch.core.common.R
import kotlinx.coroutines.flow.*
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

    private val _messages: MutableStateFlow<List<UiMessage>> = MutableStateFlow(emptyList())

    /**
     * A flow emitting the current message to display.
     */
    val message: Flow<UiMessage?> = _messages.map { it.firstOrNull() }.distinctUntilChanged()

    suspend fun emitMessage(message: UiMessage) {
        mutex.withLock {
            _messages.update { it + message }
        }
    }

    suspend fun clearMessage(id: Long) {
        mutex.withLock {
            _messages.update { messages -> messages.filterNot { it.id == id } }
        }
    }
}
