package navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

/**
 * Simple NavController for navigation with in the desktop app
 */
class NavController(private val startDestination: String) {
    // Current screen state
    var currentScreen: MutableState<String> = mutableStateOf(startDestination)
        private set

    /**
     * Function allows navigation to the input route
     * @param route route as defined in Screen class to navigate to
     */
    fun navigate(route: String) {
        if (route != currentScreen.value) {
            currentScreen.value = route
        }
    }

    /**
     * Function allows navigation back to start destination (login)
     */
    fun navigateToStart() {
        currentScreen.value = startDestination
    }
}

/**
 * Composable function to remember NavController
 */
@Composable
fun rememberNavController(startDestination: String): NavController = remember {
    NavController(startDestination)
}