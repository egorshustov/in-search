package com.egorshustov.vpoiske.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.egorshustov.vpoiske.R
import com.egorshustov.vpoiske.feature.auth.navigation.AuthDestination
import com.egorshustov.vpoiske.feature.params.navigation.ParamsDestination
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

    fun navigateTo(destination: TopLevelDestination) {
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

enum class TopLevelDestination(
    val route: String,
    @StringRes val titleResId: Int,
    @DrawableRes val drawableResId: Int
) {
    LAST_SEARCH(
        route = SearchDestination.graphDestination,
        titleResId = R.string.last_search,
        drawableResId = R.drawable.ic_baseline_format_list_bulleted_24
    ),

    NEW_SEARCH(
        route = ParamsDestination.graphDestination,
        titleResId = R.string.search_params_title,
        drawableResId = R.drawable.ic_baseline_person_search_24
    ),

    SEARCH_HISTORY(
        route = AuthDestination.graphDestination, // TODO: add route from search history feature
        titleResId = R.string.search_history,
        drawableResId = R.drawable.ic_baseline_history_24
    )
}