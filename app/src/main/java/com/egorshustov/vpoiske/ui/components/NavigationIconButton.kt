package com.egorshustov.vpoiske.ui.components

import androidx.annotation.StringRes
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.egorshustov.vpoiske.R
import com.egorshustov.vpoiske.feature.search.navigation.SearchFeatureScreens

@Composable
fun navigationIconButton(
    currentRoute: String,
    onClick: (route: String) -> Unit
): @Composable (() -> Unit)? {
    val iconImageVector: ImageVector
    @StringRes val iconContentDescriptionRes: Int
    when (currentRoute) {
        SearchFeatureScreens.MAIN.screenRoute -> {
            iconImageVector = Icons.Filled.Menu
            iconContentDescriptionRes = R.string.open_drawer
        }
        SearchFeatureScreens.PARAMS.screenRoute -> {
            iconImageVector = Icons.Filled.ArrowBack
            iconContentDescriptionRes = R.string.return_back
        }
        else -> return null
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