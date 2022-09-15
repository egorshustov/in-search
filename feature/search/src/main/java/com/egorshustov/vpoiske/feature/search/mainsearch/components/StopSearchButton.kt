package com.egorshustov.vpoiske.feature.search.mainsearch.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.egorshustov.vpoiske.core.ui.R

@Composable
internal fun StopSearchButton(onStopSearchClick: () -> Unit) {
    Box(contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
        IconButton(onClick = onStopSearchClick) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_stop_24),
                contentDescription = stringResource(R.string.search_main_stop_search),
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Preview
@Composable
internal fun StopSearchButtonPreview() {
    StopSearchButton(onStopSearchClick = {})
}