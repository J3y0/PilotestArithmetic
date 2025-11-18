package com.jeyo.pilotestarithmetic.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


private val DarkColorScheme = darkColorScheme(
    primary = MacchiatoColors.Blue,
    onPrimary = MacchiatoColors.Crust,
    primaryContainer = MacchiatoColors.Lavender,
    onPrimaryContainer = MacchiatoColors.Base,

    secondary = MacchiatoColors.Sapphire,
    onSecondary = MacchiatoColors.Crust,
    secondaryContainer = MacchiatoColors.Sky,
    onSecondaryContainer = MacchiatoColors.Base,

    tertiary = MacchiatoColors.Green,
    onTertiary = MacchiatoColors.Crust,
    tertiaryContainer = MacchiatoColors.Green,
    onTertiaryContainer = MacchiatoColors.Base,

    error = MacchiatoColors.Red,
    onError = MacchiatoColors.Crust,
    errorContainer = MacchiatoColors.Red,
    onErrorContainer = MacchiatoColors.Base,

    background = MacchiatoColors.Base,
    onBackground = MacchiatoColors.Text,

    surface = MacchiatoColors.Mantle,
    onSurface = MacchiatoColors.Text,
    surfaceVariant = MacchiatoColors.Surface0,
    onSurfaceVariant = MacchiatoColors.Subtext1,

    outline = MacchiatoColors.Overlay0,
    outlineVariant = MacchiatoColors.Surface2,

    inverseSurface = MacchiatoColors.Text,
    inverseOnSurface = MacchiatoColors.Crust,
    inversePrimary = MacchiatoColors.Lavender,

    scrim = Color.Black
)

private val LightColorScheme = lightColorScheme(
    primary = LightColors.Blue,
    onPrimary = LightColors.Crust,
    primaryContainer = LightColors.Lavender,
    onPrimaryContainer = LightColors.Base,

    secondary = LightColors.Sapphire,
    onSecondary = LightColors.Crust,
    secondaryContainer = LightColors.Sky,
    onSecondaryContainer = LightColors.Base,

    tertiary = LightColors.Green,
    onTertiary = LightColors.Crust,
    tertiaryContainer = LightColors.GreenContainer,
    onTertiaryContainer = LightColors.Base,

    error = LightColors.Red,
    onError = LightColors.Crust,
    errorContainer = LightColors.RedContainer,
    onErrorContainer = LightColors.Base,

    background = LightColors.Base,
    onBackground = LightColors.Text,

    surface = LightColors.Mantle,
    onSurface = LightColors.Text,
    surfaceVariant = LightColors.Surface0,
    onSurfaceVariant = LightColors.Subtext1,

    outline = LightColors.Overlay0,
    outlineVariant = LightColors.Surface2,

    inverseSurface = LightColors.Text,
    inverseOnSurface = LightColors.Crust,
    inversePrimary = LightColors.Lavender,

    scrim = Color.Black
)

val AppShapes = Shapes(
    medium = RoundedCornerShape(10.dp)
)

@Composable
fun PilotestArithmeticTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme =
        if (darkTheme) {
            DarkColorScheme
        } else {
            LightColorScheme
        }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = AppShapes,
        content = content
    )
}