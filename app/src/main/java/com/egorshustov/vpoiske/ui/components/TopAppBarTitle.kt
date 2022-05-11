package com.egorshustov.vpoiske.ui.components

import androidx.annotation.StringRes
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.egorshustov.auth.api.AuthScreen
import com.egorshustov.search.api.SearchScreen
import com.egorshustov.vpoiske.R

@Composable
fun topAppBarTitle(
    currentRoute: String,
    modifier: Modifier = Modifier
): @Composable () -> Unit = {

    @StringRes val screenTitleRes: Int =
        SearchScreen.values().find { it.screenRoute == currentRoute }?.titleResId
            ?: AuthScreen.values().find { it.screenRoute == currentRoute }?.titleResId
            ?: R.string.app_name

    val screenTitle = stringResource(screenTitleRes)

    Text(
        text = screenTitle,
        style = MaterialTheme.typography.h3,
        modifier = modifier
    )
}