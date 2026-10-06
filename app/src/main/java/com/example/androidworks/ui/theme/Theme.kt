package com.example.androidworks.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val LightColors = lightColorScheme(
    primary = Accent,
    onPrimary = Paper,
    primaryContainer = AccentLight,
    onPrimaryContainer = Paper,
    secondary = Gold,
    onSecondary = Paper,
    background = Paper,
    onBackground = Ink,
    surface = Paper,
    onSurface = Ink,
    surfaceVariant = PaperDark,
    onSurfaceVariant = InkSoft,
    outline = PaperDark,
    outlineVariant = PaperDark
)

private val DarkColors = darkColorScheme(
    primary = NightAccent,
    onPrimary = NightPaper,
    primaryContainer = AccentLight,
    onPrimaryContainer = NightPaper,
    secondary = Gold,
    onSecondary = NightPaper,
    background = NightPaper,
    onBackground = NightInk,
    surface = NightPaper,
    onSurface = NightInk,
    surfaceVariant = NightPaperDark,
    onSurfaceVariant = NightInkSoft,
    outline = NightPaperDark,
    outlineVariant = NightPaperDark
)

@Composable
fun AndroidWorksTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColors
        else -> LightColors
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = MuseumTypography,
        content = content
    )
}