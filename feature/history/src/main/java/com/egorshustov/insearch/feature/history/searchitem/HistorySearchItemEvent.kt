package com.egorshustov.insearch.feature.history.searchitem

import android.content.Context

internal sealed interface HistorySearchItemEvent {

    data class OnClickUserCard(val userId: Long, val context: Context) : HistorySearchItemEvent

    object OnChangeColumnCount : HistorySearchItemEvent

    data class OnMessageShown(val uiMessageId: Long) : HistorySearchItemEvent
}