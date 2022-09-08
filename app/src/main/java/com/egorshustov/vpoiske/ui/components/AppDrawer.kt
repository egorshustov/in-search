package com.egorshustov.vpoiske.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egorshustov.vpoiske.R
import com.egorshustov.vpoiske.navigation.TopLevelDestination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDrawer(
    onNavigateToTopLevelDestination: (TopLevelDestination) -> Unit,
    onChangeThemeClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ModalDrawerSheet {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(start = 24.dp, top = 120.dp, end = 24.dp)
        ) {
            TopLevelDestination.values().forEach { destination ->
                DrawerItem(
                    textResId = destination.titleResId,
                    drawableResId = destination.drawableResId,
                    onClick = { onNavigateToTopLevelDestination(destination) }
                )
            }

            Divider(modifier = Modifier.padding(8.dp))

            DrawerItem(
                textResId = R.string.app_change_theme,
                drawableResId = R.drawable.ic_baseline_wb_sunny_24,
                onClick = { onChangeThemeClick() }
            )
        }
    }
}