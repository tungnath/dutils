package theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color


object GradientColors {
    val gradientBackground = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFFF9A8B), // Peach/Orange at top
            Color(0xFFFECFEF), // Light pink
            Color(0xFFFECFEF), // Light pink middle
            Color(0xFFB8E6FF), // Light blue
            Color(0xFF6366F1)  // Purple/Blue at bottom
        )
    )

    // Card colors
    val cardBackground = Color(0xFFFDFDFD).copy(alpha = 0.25f) // Semi-transparent white
    val cardSurface = Color(0xFFFFFFFF).copy(alpha = 0.9f)     // Slightly transparent white

    // Button colors
    val primaryButton = Brush.horizontalGradient(
        colors = listOf(
            Color(0xFF6366F1), // Purple
            Color(0xFF8B5CF6)  // Lighter purple
        )
    )

    // Individual colors
    val primaryPurple = Color(0xFF6366F1)
    val lightPurple = Color(0xFF8B5CF6)
    val softPink = Color(0xFFFECFEF)
    val peachOrange = Color(0xFFFF9A8B)
    val lightBlue = Color(0xFFB8E6FF)
}

// Color palette for app
private val DarkColorPalette = darkColors(
    primary = GradientColors.primaryPurple,
    primaryVariant = GradientColors.lightPurple,
    secondary = GradientColors.softPink,
    background = Color(0xFF1A1A1A),
    surface = Color(0xFF2D2D2D).copy(alpha = 0.9f),
    error = Color(0xFFFF6B6B),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color(0xFFE0E0E0),
    onSurface = Color(0xFFE0E0E0),
    onError = Color.Black
)

private val LightColorPalette = lightColors(
    primary = GradientColors.primaryPurple,
    primaryVariant = GradientColors.lightPurple,
    secondary = GradientColors.softPink,
    secondaryVariant = GradientColors.peachOrange,
    background = Color(0xFFFDFDFD),
    surface = Color(0xFFFFFFFF).copy(alpha = 0.9f),
    error = Color(0xFFFF6B6B),
    onPrimary = Color.White,
    onSecondary = Color(0xFF333333),
    onBackground = Color(0xFF333333),
    onSurface = Color(0xFF333333),
    onError = Color.White
)

@Composable
fun GradientTheme(
    darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors, typography = Typography, shapes = Shapes, content = content
    )
}