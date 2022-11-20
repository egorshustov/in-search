package com.egorshustov.insearch.feature.search.processsearch

import com.egorshustov.insearch.core.ui.api.UiMessage

internal data class ProcessSearchState(
    val foundUsersCount: Int = 0,
    val foundUsersLimit: Int? = null,
    val message: UiMessage? = null
) {
    companion object {
        val Default = ProcessSearchState()
    }
}
