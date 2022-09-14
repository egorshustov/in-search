package com.egorshustov.vpoiske.feature.search.main_search

import android.content.Context

internal sealed interface MainSearchEvent {

    object OnAuthRequested : MainSearchEvent

    data class OnStartSearchProcess(val searchId: Long) : MainSearchEvent

    object OnStopSearchProcess : MainSearchEvent

    data class OnClickUserCard(val userId: Long, val context: Context) : MainSearchEvent

    object OnChangeColumnCount : MainSearchEvent

    data class OnMessageShown(val uiMessageId: Long) : MainSearchEvent
}