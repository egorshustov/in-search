package com.egorshustov.vpoiske.ui.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
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
            .padding(all = 5.dp)
            .height(50.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.padding(end = 8.dp),
            painter = painterResource(drawableResId),
            contentDescription = stringResource(textResId)
        )

        Text(
            modifier = Modifier.padding(bottom = 3.dp),
            text = stringResource(textResId),
            style = MaterialTheme.typography.titleMedium
        )
    }

}

@Preview
@Composable
fun DrawerItemPreview() {
    DrawerItem(
        textResId = R.string.app_new_search,
        drawableResId = R.drawable.ic_baseline_person_search_24,
        onClick = {}
    )
}