package theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Color palette for app
private val DarkColorPalette = darkColors(
    primary = Color(0xFF6750A4),
    primaryVariant = Color(0xFF7C4DFF),
    secondary = Color(0xFF03DAC6),
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E),
    error = Color(0xFFCF6679),
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color(0xFFE0E0E0),
    onSurface = Color(0xFFE0E0E0),
    onError = Color.Black
)

private val LightColorPalette = lightColors(
    primary = Color(0xFF6750A4),
    primaryVariant = Color(0xFF7C4DFF),
    secondary = Color(0xFF03DAC6),
    background = Color(0xFFF6F6F6),
    surface = Color.White,
    error = Color(0xFFB00020),
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color(0xFF121212),
    onSurface = Color(0xFF121212),
    onError = Color.White
)

@Composable
fun DUtilsTheme(
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