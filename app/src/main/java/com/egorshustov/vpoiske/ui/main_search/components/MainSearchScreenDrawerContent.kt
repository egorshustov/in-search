package com.egorshustov.vpoiske.ui.main_search.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.egorshustov.vpoiske.R

@Composable
fun MainSearchScreenDrawerContent(
    onLastSearchItemClicked: () -> Unit,
    onNewSearchItemClicked: () -> Unit,
    onSearchHistoryItemClicked: () -> Unit,
    onChangeThemeItemClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier
            .fillMaxSize()
            .padding(start = 24.dp, top = 120.dp, end = 24.dp)
    ) {
        DrawerItem(
            textResId = R.string.last_search,
            drawableResId = R.drawable.ic_baseline_format_list_bulleted_24,
            onClick = { onLastSearchItemClicked() }
        )

        DrawerItem(
            textResId = R.string.new_search,
            drawableResId = R.drawable.ic_baseline_person_search_24,
            onClick = { onNewSearchItemClicked() }
        )

        DrawerItem(
            textResId = R.string.search_history,
            drawableResId = R.drawable.ic_baseline_history_24,
            onClick = { onSearchHistoryItemClicked() }
        )

        Divider(modifier = Modifier.padding(8.dp))

        DrawerItem(
            textResId = R.string.change_theme,
            drawableResId = R.drawable.ic_baseline_wb_sunny_24,
            onClick = { onChangeThemeItemClicked() }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MainSearchScreenDrawerContentPreview() {
    MainSearchScreenDrawerContent(
        onLastSearchItemClicked = {},
        onNewSearchItemClicked = {},
        onSearchHistoryItemClicked = {},
        onChangeThemeItemClicked = {}
    )
}