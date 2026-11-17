package com.example.pokedex.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Color(0xFFD93A2B),
    onPrimary = Color.White,
    secondary = Color(0xFF3B4CCA),
    background = Color(0xFFF4F4F7),
    surface = Color.White,
    onSurface = Color(0xFF1C1B1F)
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFFB3261E),
    onPrimary = Color.White,
    secondary = Color(0xFF8C98FF),
    background = Color(0xFF121214),
    surface = Color(0xFF1E1E22),
    onSurface = Color(0xFFE6E1E5)
)

@Composable
fun PokedexTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = if (darkTheme) DarkColors else LightColors, content = content)
}
