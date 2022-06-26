package com.egorshustov.vpoiske.feature.params

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
internal fun ParamsSearchScreen(
    state: ParamsState,
    onTriggerEvent: (ParamsEvent) -> Unit,
    modifier: Modifier
) {
    Text(text = "ParamsSearchScreen")
}