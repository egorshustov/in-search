package com.egorshustov.vpoiske.ui

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.*
import androidx.navigation.compose.rememberNavController
import com.egorshustov.vpoiske.core.ui.theme.VPoiskeTheme
import com.egorshustov.vpoiske.navigation.AppNavHost
import com.egorshustov.vpoiske.navigation.AppTopLevelNavigation
import com.egorshustov.vpoiske.navigation.TopLevelDestination
import com.egorshustov.vpoiske.ui.components.AppDrawer
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

        var gesturesEnabled by remember { mutableStateOf(true) } // todo use it to disable gestures when necessary

        /*val routesWithDrawer = remember { TopLevelDestination.values().map { it.destination } }
        val currentRoute = navController.getCurrentRoute()
        if (currentRoute in routesWithDrawer) {

        }*/
        ModalNavigationDrawer(
            gesturesEnabled = gesturesEnabled,
            drawerState = drawerState,
            drawerContent = {
                AppDrawer(
                    onNavigateToTopLevelDestination = { destination ->
                        coroutineScope.launch { drawerState.close() }
                        if (destination != TopLevelDestination.LAST_SEARCH) {
                            // (No need to navigate to last search screen,
                            // because if we're able to click on this, we're already in here)
                            appTopLevelNavigation.navigateTo(destination)
                        }
                    },
                    onChangeThemeClick = {}
                )
            })
        {
            AppNavHost(
                navController = navController,
                openDrawer = { coroutineScope.launch { drawerState.open() } },
            )
        }
    }
}