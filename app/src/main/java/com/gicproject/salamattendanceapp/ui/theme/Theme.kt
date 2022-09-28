package com.gicproject.salamattendanceapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = greenSalam,
    onPrimary = orange,
    primaryVariant = Purple700,
    secondary = purple,
    background = navyBlueDark,
    onBackground = Color.White,
    onSurface = Color.White,
    surface = navyBlueDark,
    secondaryVariant = navyBlueDarkShade,
)

private val LightColorPalette = lightColors(
    primary = greenSalam,
    onPrimary = Color.White,
    primaryVariant = Purple700,
    secondary = purple,
    background = Color.White,
    onBackground = greenSalam,
    onSurface = greenSalam,
    surface = Color.White,
    secondaryVariant = navyBlueDarkShade,
    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun ClassRoomAttendanceTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}