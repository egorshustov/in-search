package com.egorshustov.vpoiske.ui.main_search.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.egorshustov.vpoiske.R

@Composable
fun DrawerItem(
    @StringRes textResId: Int,
    @DrawableRes drawableResId: Int,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(all = 15.dp)
            .clickable(onClick = onClick)
    ) {
        Icon(
            modifier = Modifier.padding(end = 8.dp),
            painter = painterResource(drawableResId),
            contentDescription = stringResource(R.string.new_search)
        )

        Text(
            text = stringResource(textResId),
            style = MaterialTheme.typography.subtitle1
        )
    }
}