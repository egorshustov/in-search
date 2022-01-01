package com.egorshustov.vpoiske.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.egorshustov.vpoiske.ui.navigation.SearchScreen

/**
 * Starts recomposition when back stack changes
 * @return current route of navigation back stack
 */
@Composable
fun NavController.getCurrentRoute(): String {
    val navBackStackEntry by currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route ?: SearchScreen.MAIN.screenRoute
}