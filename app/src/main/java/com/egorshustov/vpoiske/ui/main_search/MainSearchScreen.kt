package com.egorshustov.vpoiske.ui.main_search

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.egorshustov.vpoiske.R
import com.egorshustov.vpoiske.ui.main_search.components.MainSearchScreenContent
import com.egorshustov.vpoiske.ui.main_search.components.MainSearchScreenDrawerContent
import kotlinx.coroutines.launch

@Composable
fun MainSearchScreen(
    onLastSearchItemClicked: () -> Unit,
    onNewSearchItemClicked: () -> Unit,
    onSearchHistoryItemClicked: () -> Unit,
    onChangeThemeItemClicked: () -> Unit
) {
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.app_name))
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            coroutineScope.launch {
                                scaffoldState.drawerState.open()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = stringResource(R.string.open_drawer)
                        )
                    }
                },
                backgroundColor = Color.Blue,
                contentColor = Color.White,
                elevation = 12.dp
            )
        },
        drawerContent = {
            MainSearchScreenDrawerContent(
                onLastSearchItemClicked = onLastSearchItemClicked,
                onNewSearchItemClicked = onNewSearchItemClicked,
                onSearchHistoryItemClicked = onSearchHistoryItemClicked,
                onChangeThemeItemClicked = onChangeThemeItemClicked
            )
        }
    ) {
        MainSearchScreenContent()
    }
}

@Preview
@Composable
fun MainSearchScreenPreview() {
    MainSearchScreen(
        onLastSearchItemClicked = {},
        onNewSearchItemClicked = {},
        onSearchHistoryItemClicked = {},
        onChangeThemeItemClicked = {}
    )
}