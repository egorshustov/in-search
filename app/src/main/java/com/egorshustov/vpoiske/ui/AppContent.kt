package com.egorshustov.vpoiske.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.egorshustov.vpoiske.core.navigation.getCurrentRoute
import com.egorshustov.vpoiske.feature.auth.navigation.AuthDestination
import com.egorshustov.vpoiske.feature.search.navigation.SearchDestination
import com.egorshustov.vpoiske.feature.search.navigation.SearchScreen
import com.egorshustov.vpoiske.ui.components.drawerContent
import com.egorshustov.vpoiske.ui.components.navigationIconButton
import com.egorshustov.vpoiske.ui.components.topAppBarTitle
import com.egorshustov.vpoiske.ui.models.ClickedDrawerItem
import com.egorshustov.vpoiske.ui.theme.VPoiskeTheme
import com.egorshustov.vpoiske.utils.BackPressHandler
import kotlinx.coroutines.launch

@Composable
fun AppContent() {
    VPoiskeTheme {
        val coroutineScope = rememberCoroutineScope()
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scaffoldState = rememberScaffoldState(drawerState)
        val navController = rememberNavController()
        if (drawerState.isOpen) {
            BackPressHandler {
                coroutineScope.launch { drawerState.close() }
            }
        }
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                TopAppBar(
                    title = topAppBarTitle(currentRoute = navController.getCurrentRoute()),
                    navigationIcon = navigationIconButton(
                        currentRoute = navController.getCurrentRoute(),
                        onClick = { route ->
                            when (route) {
                                SearchScreen.MAIN.screenRoute ->
                                    coroutineScope.launch { scaffoldState.drawerState.open() }
                                else -> navController.popBackStack()
                            }
                        }
                    ),
                    elevation = 12.dp
                )
            },
            drawerContent = drawerContent(
                currentRoute = navController.getCurrentRoute(),
                onItemClick = { item ->
                    coroutineScope.launch { scaffoldState.drawerState.close() }
                    when (item) {
                        ClickedDrawerItem.LAST_SEARCH -> {
                            // No need to navigate to search main screen,
                            // because if we're able to click this, we're already in here
                        }
                        ClickedDrawerItem.NEW_SEARCH ->
                            navController.navigate(SearchDestination.screenRoute)
                        ClickedDrawerItem.SEARCH_HISTORY -> {
                            // TODO change later (this is only for testing):
                            navController.navigate(AuthDestination.graphDestination)
                        }
                        ClickedDrawerItem.CHANGE_THEME -> {
                        }
                    }
                },
            )
        ) { innerPaddingModifier ->
            AppNavGraph(
                navController = navController,
                modifier = Modifier.padding(innerPaddingModifier)
            )
        }
    }
}