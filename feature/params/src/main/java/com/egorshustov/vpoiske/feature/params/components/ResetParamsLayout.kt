package com.egorshustov.vpoiske.feature.params.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.egorshustov.vpoiske.feature.params.R

@Composable
internal fun ResetParamsLayout(
    areSelectionParamsDefault: Boolean,
    onResetParamsClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = stringResource(R.string.params_search_params),
            style = MaterialTheme.typography.titleMedium
        )
        TextButton(
            enabled = !areSelectionParamsDefault,
            modifier = Modifier.padding(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            onClick = onResetParamsClick
        ) {
            Text(
                text = stringResource(R.string.params_reset),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Preview
@Composable
internal fun ResetParamsLayoutPreview() {
    ResetParamsLayout(
        areSelectionParamsDefault = false,
        onResetParamsClick = {}
    )
}