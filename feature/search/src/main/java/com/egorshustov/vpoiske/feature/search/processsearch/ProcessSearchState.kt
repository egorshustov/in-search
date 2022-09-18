package com.egorshustov.vpoiske.feature.search.processsearch

import com.egorshustov.vpoiske.core.ui.api.UiMessage

internal data class ProcessSearchState(
    val foundUsersCount: Int = 0,
    val foundUsersLimit: Int? = null,
    val message: UiMessage? = null
) {
    companion object {
        val Default = ProcessSearchState()
    }
}