package com.egorshustov.insearch.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

/**
 * Gets the current navigation back stack entry unique route.
 * @return current route of navigation back stack
 */
@Composable
fun NavController.getCurrentRoute(): String {
    val navBackStackEntry by currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route.orEmpty()
}