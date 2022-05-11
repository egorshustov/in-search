package com.egorshustov.vpoiske.ui.components

import androidx.annotation.StringRes
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.egorshustov.auth.api.AuthScreen
import com.egorshustov.search.api.SearchScreen

@Composable
fun topAppBarTitle(
    currentRoute: String,
    modifier: Modifier = Modifier
): @Composable () -> Unit = {
    @StringRes val screenTitleRes: Int? =
        SearchScreen.values().find { it.screenRoute == currentRoute }?.titleResId
            ?: AuthScreen.values().find { it.screenRoute == currentRoute }?.titleResId
    val screenTitle = screenTitleRes?.let { stringResource(it) }.orEmpty()
    Text(
        text = screenTitle,
        style = MaterialTheme.typography.h3,
        modifier = modifier
    )
}