package com.egorshustov.vpoiske.navigation

import androidx.annotation.StringRes
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.egorshustov.vpoiske.R
import com.egorshustov.vpoiske.feature.search.navigation.SearchDestination

/**
 * Routes for the different top level destinations in the application. Each of these destinations
 * can contain one or more screens (based on the window size). Navigation from one screen to the
 * next within a single destination will be handled directly in composables.
 */

/**
 * Models the navigation top level actions in the app.
 */
class AppTopLevelNavigation(private val navController: NavHostController) {

    fun navigateTo(destination: TopLevelDestinations) {
        navController.navigate(destination.route) {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }
    }
}

enum class TopLevelDestinations(
    val route: String,
    @StringRes val titleResId: Int
) {
    LAST_SEARCH(SearchDestination.graphDestination, R.string.search_main_title),
    NEW_SEARCH(SearchDestination.screenRoute, R.string.search_params_title)
}