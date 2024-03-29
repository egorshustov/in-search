package com.egorshustov.insearch.ui

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.*
import androidx.navigation.compose.rememberNavController
import com.egorshustov.insearch.core.navigation.getCurrentRoute
import com.egorshustov.insearch.core.ui.theme.InSearchTheme
import com.egorshustov.insearch.navigation.AppNavHost
import com.egorshustov.insearch.navigation.AppTopLevelNavigation
import com.egorshustov.insearch.navigation.TopLevelDestination
import com.egorshustov.insearch.ui.components.AppDrawer
import com.egorshustov.insearch.utils.BackPressHandler
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppContent() {
    InSearchTheme {
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

        var gesturesEnabled by remember { mutableStateOf(true) }

        val routesWithDrawer = remember { TopLevelDestination.values().map { it.destination } }
        val currentRoute = navController.getCurrentRoute()
        gesturesEnabled = currentRoute in routesWithDrawer

        ModalNavigationDrawer(
            gesturesEnabled = gesturesEnabled,
            drawerState = drawerState,
            drawerContent = {
                AppDrawer(
                    currentRoute = navController.getCurrentRoute(),
                    onNavigateToTopLevelDestination = { destination ->
                        coroutineScope.launch { drawerState.close() }
                        appTopLevelNavigation.navigateTo(destination)
                    },
                    closeDrawer = {
                        coroutineScope.launch { drawerState.close() }
                    }
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