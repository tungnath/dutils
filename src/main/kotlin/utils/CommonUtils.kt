package utils

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

object CommonUtils {

    @Composable
    fun isDarkTheme(): Boolean {
        // Parameterize and check for other resources like colors, shapes, typography
        return !MaterialTheme.colors.isLight
    }

}