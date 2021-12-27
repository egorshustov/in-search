package com.egorshustov.vpoiske.ui.main_search.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.egorshustov.vpoiske.R
import com.egorshustov.vpoiske.ui.navigation.AuthScreen
import com.egorshustov.vpoiske.ui.navigation.SearchScreen

@Composable
fun navigationIconButton(
    currentRoute: String,
    onClick: () -> Unit
): @Composable (() -> Unit)? {
    var iconImageVector: ImageVector = Icons.Filled.Menu
    when (currentRoute) {
        AuthScreen.LOGIN.screenRoute -> return null
        SearchScreen.MAIN.screenRoute -> iconImageVector = Icons.Filled.Menu
        SearchScreen.PARAMS.screenRoute -> iconImageVector = Icons.Filled.ArrowBack
        else -> {}
    }
    return {
        IconButton(onClick = onClick) {
            Icon(
                imageVector = iconImageVector,
                contentDescription = stringResource(R.string.open_drawer)
            )
        }
    }
}