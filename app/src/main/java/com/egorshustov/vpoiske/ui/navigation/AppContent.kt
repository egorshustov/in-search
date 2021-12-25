package com.egorshustov.vpoiske.ui.navigation

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.egorshustov.vpoiske.R
import com.egorshustov.vpoiske.ui.login_auth.LoginAuthScreen
import com.egorshustov.vpoiske.ui.main_search.MainSearchScreen
import com.egorshustov.vpoiske.ui.main_search.components.NavigationIconButton
import com.egorshustov.vpoiske.ui.main_search.components.drawerContent
import com.egorshustov.vpoiske.ui.params_search.ParamsSearchScreen
import com.egorshustov.vpoiske.ui.theme.VPoiskeTheme
import kotlinx.coroutines.launch

@Composable
fun AppContent() {
    VPoiskeTheme {
        val navController = rememberNavController()
        val coroutineScope = rememberCoroutineScope()
        val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(R.string.app_name)) },
                    navigationIcon = {
                        NavigationIconButton(
                            onClick = {
                                coroutineScope.launch { scaffoldState.drawerState.open() }
                            }
                        )
                    },
                    backgroundColor = Color.Blue,
                    contentColor = Color.White,
                    elevation = 12.dp
                )
            },
            drawerContent = drawerContent(navController)
        ) {
            NavHost(
                navController = navController,
                startDestination = SearchScreen.graphRoute
            ) {
                searchGraph(navController)
                authGraph(navController)
            }
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