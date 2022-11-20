package com.egorshustov.insearch.feature.history.searchlist

internal sealed interface HistorySearchListEvent {

    // todo: call this from UI when material3 will provide SwipeToDismiss
    data class OnDismissSearchItem(val searchId: Long) : HistorySearchListEvent

    data class OnMessageShown(val uiMessageId: Long) : HistorySearchListEvent
}