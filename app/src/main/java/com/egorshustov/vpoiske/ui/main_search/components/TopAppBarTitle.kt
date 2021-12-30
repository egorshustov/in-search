package com.egorshustov.vpoiske.ui.main_search.components

import androidx.annotation.StringRes
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.egorshustov.vpoiske.ui.navigation.AuthScreen
import com.egorshustov.vpoiske.ui.navigation.SearchScreen

@Composable
fun topAppBarTitle(
    currentRoute: String,
    modifier: Modifier = Modifier
): @Composable () -> Unit = {
    @StringRes val screenTitleRes: Int? =
        SearchScreen.values().find { it.screenRoute == currentRoute }?.titleRes
            ?: AuthScreen.values().find { it.screenRoute == currentRoute }?.titleRes
    val screenTitle = screenTitleRes?.let { stringResource(it) }.orEmpty()
    Text(
        text = screenTitle,
        style = MaterialTheme.typography.h3,
        modifier = modifier
    )
}