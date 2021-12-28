package com.egorshustov.vpoiske.ui.main_search.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egorshustov.vpoiske.R
import com.egorshustov.vpoiske.ui.navigation.AuthScreen
import com.egorshustov.vpoiske.ui.navigation.SearchScreen

@Composable
fun drawerContent(
    currentRoute: String,
    onItemClick: (itemRoute: String) -> Unit,
    modifier: Modifier = Modifier
): @Composable (ColumnScope.() -> Unit)? {
    val routesWithDrawer = remember { SearchScreen.values().map { it.screenRoute } }
    return if (currentRoute in routesWithDrawer) {
        {
            Column(
                modifier
                    .fillMaxSize()
                    .padding(start = 24.dp, top = 120.dp, end = 24.dp)
            ) {
                DrawerItem(
                    textResId = R.string.last_search,
                    drawableResId = R.drawable.ic_baseline_format_list_bulleted_24,
                    onClick = {
                        // TODO: this is only for testing, will be removed later:
                        onItemClick(AuthScreen.LOGIN.screenRoute)
                    }
                )

                DrawerItem(
                    textResId = R.string.new_search,
                    drawableResId = R.drawable.ic_baseline_person_search_24,
                    onClick = { onItemClick(SearchScreen.PARAMS.screenRoute) }
                )

                DrawerItem(
                    textResId = R.string.search_history,
                    drawableResId = R.drawable.ic_baseline_history_24,
                    onClick = {}
                )

                Divider(modifier = Modifier.padding(8.dp))

                DrawerItem(
                    textResId = R.string.change_theme,
                    drawableResId = R.drawable.ic_baseline_wb_sunny_24,
                    onClick = {}
                )
            }
        }
    } else null
}