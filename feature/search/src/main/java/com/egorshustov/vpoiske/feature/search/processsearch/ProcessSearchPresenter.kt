package com.egorshustov.vpoiske.feature.search.processsearch

import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow

internal interface ProcessSearchPresenter {

    val state: StateFlow<ProcessSearchState>

    fun startSearch(): Job

    fun onMessageShown(uiMessageId: Long)
}