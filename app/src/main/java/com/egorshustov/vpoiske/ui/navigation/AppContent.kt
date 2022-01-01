package com.egorshustov.vpoiske.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.egorshustov.vpoiske.ui.login_auth.LoginAuthScreen
import com.egorshustov.vpoiske.ui.main_search.MainSearchScreen
import com.egorshustov.vpoiske.ui.main_search.components.drawerContent
import com.egorshustov.vpoiske.ui.main_search.components.navigationIconButton
import com.egorshustov.vpoiske.ui.main_search.components.topAppBarTitle
import com.egorshustov.vpoiske.ui.params_search.ParamsSearchScreen
import com.egorshustov.vpoiske.ui.theme.VPoiskeTheme
import com.egorshustov.vpoiske.utils.BackPressHandler
import com.egorshustov.vpoiske.utils.getCurrentRoute
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
                onItemClick = { itemRoute ->
                    coroutineScope.launch { scaffoldState.drawerState.close() }
                    navController.navigate(itemRoute)
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

fun NavGraphBuilder.searchGraph(navController: NavController) {
    navigation(
        startDestination = SearchScreen.MAIN.screenRoute,
        route = SearchScreen.graphRoute
    ) {
        composable(
            route = SearchScreen.MAIN.screenRoute
        ) { navBackStackEntry ->
            MainSearchScreen()
        }
        composable(
            route = SearchScreen.PARAMS.screenRoute
        ) { navBackStackEntry ->
            ParamsSearchScreen()
        }
    }
}

fun NavGraphBuilder.authGraph(navController: NavController) {
    navigation(
        startDestination = AuthScreen.LOGIN.screenRoute,
        route = AuthScreen.graphRoute
    ) {
        composable(
            route = AuthScreen.LOGIN.screenRoute
        ) { navBackStackEntry ->
            LoginAuthScreen()
        }
    }
}