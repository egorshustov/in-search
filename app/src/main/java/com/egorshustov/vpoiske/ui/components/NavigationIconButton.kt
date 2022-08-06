package com.egorshustov.vpoiske.ui.components

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.egorshustov.vpoiske.R
import com.egorshustov.vpoiske.navigation.TopLevelDestination

@Composable
fun navigationIconButton(
    currentRoute: String,
    onClick: (route: String) -> Unit
): @Composable () -> Unit {
    val iconImageVector: ImageVector
    @StringRes val iconContentDescriptionRes: Int
    when (currentRoute) {
        TopLevelDestination.LAST_SEARCH.destination -> {
            iconImageVector = Icons.Filled.Menu
            iconContentDescriptionRes = R.string.app_open_drawer
        }
        TopLevelDestination.NEW_SEARCH.destination,
        TopLevelDestination.SEARCH_HISTORY.destination -> {
            iconImageVector = Icons.Filled.ArrowBack
            iconContentDescriptionRes = R.string.app_return_back
        }
        else -> return { }
    }
    return {
        IconButton(onClick = { onClick(currentRoute) }) {
            Icon(
                imageVector = iconImageVector,
                contentDescription = stringResource(iconContentDescriptionRes)
            )
        }
    }
}