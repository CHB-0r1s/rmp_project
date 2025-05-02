package ru.itmo.se.mad.ui.theme


import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF006496),
    secondary = Color(0xFF4A6572),
    tertiary = Color(0xFF7F525D),
    surface = Color(0xFFF7F9FC),
    surfaceVariant = Color(0xFFE3E7ED),
    onSurface = Color(0xFF1A1C1E),
    onSurfaceVariant = Color(0xFF43474E),
    background = Color(0xFFF7F9FC),
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF83C5F7),
    secondary = Color(0xFFB1C7D4),
    tertiary = Color(0xFFEFB8C8),
    surface = Color(0xFF1A1C1E),
    surfaceVariant = Color(0xFF43474E),
    onSurface = Color(0xFFE3E7ED),
    onSurfaceVariant = Color(0xFFC4C7D0),
    background = Color(0xFF1A1C1E),
)

@Composable
fun AddWaterWidget(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colors,
      //  typography = kotlin.text.Typography,
        content = content
    )
}
