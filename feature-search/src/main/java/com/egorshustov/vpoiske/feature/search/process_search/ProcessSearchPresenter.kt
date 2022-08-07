package com.egorshustov.vpoiske.feature.search.process_search

import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow

internal interface ProcessSearchPresenter {

    val state: StateFlow<ProcessSearchState>

    suspend fun startSearch(searchId: Long): Job

    fun clearUiMessage(id: Long)
}