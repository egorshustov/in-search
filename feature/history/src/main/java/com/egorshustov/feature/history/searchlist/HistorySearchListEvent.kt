package com.egorshustov.feature.history.searchlist

internal sealed interface HistorySearchListEvent {

    data class OnDismissSearchItem(val searchId: Long) : HistorySearchListEvent

    data class OnMessageShown(val uiMessageId: Long) : HistorySearchListEvent
}