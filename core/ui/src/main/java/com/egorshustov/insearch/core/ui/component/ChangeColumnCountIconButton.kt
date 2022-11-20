package com.egorshustov.insearch.core.ui.component

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.egorshustov.insearch.core.ui.R

@Composable
fun ChangeColumnCountIconButton(onChangeColumnCountClick: () -> Unit) {
    IconButton(onClick = onChangeColumnCountClick) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_baseline_view_column_24),
            contentDescription = stringResource(R.string.search_main_change_view),
            tint = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview
@Composable
fun ChangeColumnCountIconButtonPreview() {
    ChangeColumnCountIconButton(onChangeColumnCountClick = {})
}