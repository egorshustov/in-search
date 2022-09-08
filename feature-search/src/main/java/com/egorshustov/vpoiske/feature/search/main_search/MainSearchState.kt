package com.egorshustov.vpoiske.feature.search.main_search

import androidx.compose.runtime.Immutable
import com.egorshustov.vpoiske.core.model.data.User
import com.egorshustov.vpoiske.core.ui.api.UiMessage

@Immutable
internal data class MainSearchState(
    val users: List<User> = emptyList(),
    val searchProcessPercentage: Int? = null,
    val isSearchRunning: Boolean = false,
    val isAuthRequired: Boolean = false,
    val isLoading: Boolean = false,
    val message: UiMessage? = null
) {
    companion object {
        val Empty = MainSearchState()
    }
}