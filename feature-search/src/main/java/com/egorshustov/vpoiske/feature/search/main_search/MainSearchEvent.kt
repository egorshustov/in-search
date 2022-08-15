package com.egorshustov.vpoiske.feature.search.main_search

import android.content.Context

internal sealed interface MainSearchEvent {

    object OnAuthRequested : MainSearchEvent

    data class OnStartSearchProcess(val searchId: Long, val appContext: Context) : MainSearchEvent

    data class OnClickUserCard(val userId: Long, val context: Context) : MainSearchEvent
}