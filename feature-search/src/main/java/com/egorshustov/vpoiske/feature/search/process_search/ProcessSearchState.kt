package com.egorshustov.vpoiske.feature.search.process_search

import com.egorshustov.vpoiske.core.ui.api.UiMessage

internal data class ProcessSearchState(
    val foundUsersCount: Int = 0,
    val message: UiMessage? = null
) {
    companion object {
        val Empty = ProcessSearchState()
    }
}
