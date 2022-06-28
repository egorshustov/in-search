package com.egorshustov.vpoiske.feature.params.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.egorshustov.vpoiske.feature.params.ParamsEvent
import com.egorshustov.vpoiske.feature.params.ParamsState

@Composable
internal fun ParamsScreen(
    state: ParamsState,
    onTriggerEvent: (ParamsEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Text(text = "ParamsSearchScreen")
}