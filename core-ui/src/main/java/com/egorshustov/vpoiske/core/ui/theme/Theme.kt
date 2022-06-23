package com.egorshustov.vpoiske.core.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorScheme = darkColorScheme(
    primary = ColorNightRider,
    secondary = ColorDenim
)

private val LightColorScheme = lightColorScheme(
    primary = ColorSolitude,
    secondary = ColorTropicalBlue
)

@Composable
fun VPoiskeTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(if (darkTheme) ColorNightRider else ColorSolitude)

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        shapes = AppShapes,
        content = content
    )
}