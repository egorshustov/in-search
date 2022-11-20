package com.egorshustov.insearch.feature.history.searchlist

import androidx.compose.runtime.Immutable
import com.egorshustov.insearch.core.ui.api.UiMessage

@Immutable
internal data class HistorySearchListState(
    val isLoading: Boolean = false,
    val message: UiMessage? = null
) {
    companion object {
        val Default = HistorySearchListState()
    }
}