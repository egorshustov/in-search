package com.egorshustov.vpoiske.feature.search.mainsearch

import androidx.compose.runtime.Immutable
import com.egorshustov.vpoiske.core.model.data.User
import com.egorshustov.vpoiske.core.ui.api.UiMessage

private const val DEFAULT_COLUMN_COUNT = 3

@Immutable
internal data class MainSearchState(
    val users: List<User> = emptyList(),
    val columnCount: Int = DEFAULT_COLUMN_COUNT,
    val isSearchRunning: Boolean = false,
    val searchProcessValue: Float = 0f,
    val isAuthRequired: Boolean = false,
    val isLoading: Boolean = false,
    val message: UiMessage? = null
) {
    companion object {
        val Default = MainSearchState()
    }
}