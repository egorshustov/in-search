package com.egorshustov.feature.history.searchlist

internal sealed interface HistorySearchListEvent {

    data class OnClickSearch(val searchId: Long) : HistorySearchListEvent

    data class OnDismissSearch(val searchId: Long) : HistorySearchListEvent

    data class OnMessageShown(val uiMessageId: Long) : HistorySearchListEvent
}