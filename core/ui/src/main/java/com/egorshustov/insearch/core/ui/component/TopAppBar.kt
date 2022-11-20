package com.egorshustov.insearch.core.ui.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopAppBar(
    @StringRes titleRes: Int,
    navigationIcon: ImageVector,
    @StringRes navigationIconContentDescriptionRes: Int,
    actions: @Composable RowScope.() -> Unit = {},
    modifier: Modifier = Modifier,
    colors: TopAppBarColors = TopAppBarDefaults.smallTopAppBarColors(),
    onNavigationClick: () -> Unit = {}
) {
    TopAppBar(
        title = { Text(text = stringResource(id = titleRes)) },
        navigationIcon = {
            IconButton(onClick = onNavigationClick) {
                Icon(
                    imageVector = navigationIcon,
                    contentDescription = stringResource(id = navigationIconContentDescriptionRes),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        actions = actions,
        colors = colors,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview("AppTopAppBar")
@Composable
fun NiaTopAppBarPreview() {
    AppTopAppBar(
        titleRes = android.R.string.untitled,
        navigationIcon = Icons.Filled.Menu,
        navigationIconContentDescriptionRes = android.R.string.untitled
    )
}
