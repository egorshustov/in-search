package com.egorshustov.vpoiske.ui.components

import androidx.annotation.StringRes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.egorshustov.vpoiske.R
import com.egorshustov.vpoiske.feature.auth.navigation.AuthFeatureScreens
import com.egorshustov.vpoiske.feature.search.navigation.SearchFeatureScreens

@Composable
fun topAppBarTitle(
    currentRoute: String,
    modifier: Modifier = Modifier
): @Composable () -> Unit = {

    @StringRes val screenTitleRes: Int =
        SearchFeatureScreens.values().find { it.screenRoute == currentRoute }?.titleResId
            ?: AuthFeatureScreens.values().find { it.screenRoute == currentRoute }?.titleResId
            ?: R.string.app_name

    val screenTitle = stringResource(screenTitleRes)

    Text(
        text = screenTitle,
        style = MaterialTheme.typography.headlineLarge,
        modifier = modifier
    )
}