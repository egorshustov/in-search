package com.egorshustov.vpoiske.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.egorshustov.vpoiske.R

@Composable
fun MainSearchScreen() {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = {
            MainSearchScreenDrawer()
        }
    ) {
    }
}

private val drawerItems = listOf(
    "Последний поиск",
    "Новый поиск",
    "История поисков",
    "Сменить тему"
)

@Composable
fun MainSearchScreenDrawer(modifier: Modifier = Modifier) {
    Column(
        modifier
            .fillMaxSize()
            .padding(start = 24.dp, top = 48.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = stringResource(R.string.open_drawer)
        )
        for (screen in drawerItems) {
            Spacer(Modifier.height(24.dp))
            Text(text = screen, style = MaterialTheme.typography.h4)
        }
    }
}