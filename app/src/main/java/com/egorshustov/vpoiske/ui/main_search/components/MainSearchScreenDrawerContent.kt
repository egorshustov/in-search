package com.egorshustov.vpoiske.ui.main_search.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.egorshustov.vpoiske.R
import com.egorshustov.vpoiske.ui.main_search.MainSearchScreen

@Composable
fun MainSearchScreenDrawerContent(
    modifier: Modifier = Modifier,
    onLastSearchItemClicked: () -> Unit,
    onNewSearchItemClicked: () -> Unit,
    onSearchHistoryItemClicked: () -> Unit,
    onChangeThemeItemClicked: () -> Unit
) {
    Column(
        modifier
            .fillMaxSize()
            .padding(start = 24.dp, top = 120.dp, end = 24.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(all = 15.dp)
                .clickable { onLastSearchItemClicked() }
        ) {
            Icon(
                modifier = Modifier.padding(end = 8.dp),
                painter = painterResource(R.drawable.ic_baseline_format_list_bulleted_24),
                contentDescription = stringResource(R.string.last_search)
            )

            Text(
                text = stringResource(R.string.last_search),
                style = MaterialTheme.typography.subtitle1
            )
        }

        Row(
            modifier = Modifier
                .padding(all = 15.dp)
                .clickable { onNewSearchItemClicked() }
        ) {
            Icon(
                modifier = Modifier.padding(end = 8.dp),
                painter = painterResource(R.drawable.ic_baseline_person_search_24),
                contentDescription = stringResource(R.string.new_search)
            )

            Text(
                text = stringResource(R.string.new_search),
                style = MaterialTheme.typography.subtitle1
            )
        }

        Row(
            modifier = Modifier
                .padding(all = 15.dp)
                .clickable { onSearchHistoryItemClicked() }
        ) {
            Icon(
                modifier = Modifier.padding(end = 8.dp),
                painter = painterResource(R.drawable.ic_baseline_history_24),
                contentDescription = stringResource(R.string.search_history)
            )

            Text(
                text = stringResource(R.string.search_history),
                style = MaterialTheme.typography.subtitle1
            )
        }

        Divider(modifier = Modifier.padding(8.dp))

        Row(
            modifier = Modifier
                .padding(all = 15.dp)
                .clickable { onChangeThemeItemClicked() }
        ) {
            Icon(
                modifier = Modifier.padding(end = 8.dp),
                painter = painterResource(R.drawable.ic_baseline_wb_sunny_24),
                contentDescription = stringResource(R.string.change_theme)
            )

            Text(
                text = stringResource(R.string.change_theme),
                style = MaterialTheme.typography.subtitle1
            )
        }
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