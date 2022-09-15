package com.egorshustov.feature.history.searchitem

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
internal fun HistorySearchItemRoute(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    Text(text = "HistorySearchItemRoute")
}