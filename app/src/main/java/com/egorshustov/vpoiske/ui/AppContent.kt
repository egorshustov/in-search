package com.egorshustov.vpoiske.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.egorshustov.vpoiske.core.navigation.getCurrentRoute
import com.egorshustov.vpoiske.core.ui.theme.VPoiskeTheme
import com.egorshustov.vpoiske.navigation.AppNavHost
import com.egorshustov.vpoiske.navigation.AppTopLevelNavigation
import com.egorshustov.vpoiske.navigation.TopLevelDestination
import com.egorshustov.vpoiske.ui.components.drawerContent
import com.egorshustov.vpoiske.ui.components.navigationIconButton
import com.egorshustov.vpoiske.ui.components.topAppBarTitle
import com.egorshustov.vpoiske.utils.BackPressHandler
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppContent() {
    VPoiskeTheme {
        val coroutineScope = rememberCoroutineScope()
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

        val navController = rememberNavController()
        val appTopLevelNavigation = remember(navController) {
            AppTopLevelNavigation(navController)
        }

        if (drawerState.isOpen) {
            BackPressHandler {
                coroutineScope.launch { drawerState.close() }
            }
        }

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = drawerContent(
                currentRoute = navController.getCurrentRoute(),
                onNavigateToTopLevelDestination = { destination ->
                    coroutineScope.launch { drawerState.close() }
                    if (destination != TopLevelDestination.LAST_SEARCH) {
                        // (No need to navigate to last search screen,
                        // because if we're able to click on this, we're already in here)
                        appTopLevelNavigation.navigateTo(destination)
                    }
                },
                onChangeThemeClick = {}
            ))
        {
            Scaffold(
                topBar = {
                    SmallTopAppBar(
                        title = topAppBarTitle(currentRoute = navController.getCurrentRoute()),
                        navigationIcon = navigationIconButton(
                            currentRoute = navController.getCurrentRoute(),
                            onClick = { route ->
                                when (route) {
                                    TopLevelDestination.LAST_SEARCH.destination ->
                                        coroutineScope.launch { drawerState.open() }
                                    else -> navController.popBackStack()
                                }
                            }
                        ),
                    )
                },
            ) { innerPaddingModifier ->

                AppNavHost(
                    navController = navController,
                    modifier = Modifier.padding(innerPaddingModifier)
                )
            }
        }
    }
}