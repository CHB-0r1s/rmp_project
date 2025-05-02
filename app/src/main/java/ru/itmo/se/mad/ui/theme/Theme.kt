package ru.itmo.se.mad.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

//private val DarkColorScheme = darkColorScheme(
//    primary = ,
//    secondary = ,
//    tertiary =
//)

private val LightColorScheme = lightColorScheme(
    primary = White,
    secondary = Black,
    tertiary = WaterBlue

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}



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
fun WaterTrackerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}