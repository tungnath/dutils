import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import screens.NotificationScreen

@Composable
@Preview
fun App() {

//    // Check preference store for existing session to decide start route
//    var startRoute = Screen.Login.route
//    val prefs = Preferences.userRoot().node("dUtils")
//
//    if (prefs.keys().contains("login_user") && prefs.get("login_user", null) != null) {
//
//        val user: String = prefs.get("login_user", null)
//        val storedEmail = prefs.get("${user.lowercase(getDefault())}_em", null)
//        val storedPass = prefs.get("${user.lowercase(getDefault())}_pwd", null)
//
//        val isValid = storedEmail != null && !storedEmail.isBlank() && storedPass != null && !storedPass.isBlank()
//
//        if (isValid) {
//            startRoute = Screen.Home.route
//        }
//    }
//
//    // Create navigation controller with Login as start destination
//    val navController = rememberNavController(
//        startDestination = startRoute
//    )
//
//    GradientTheme {
//        // Use NavigationHost to handle routing
//        NavigationHost(navController = navController)
//    }


    MaterialTheme {
        NotificationScreen()
    }

}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication, title = "dUtils - Desktop Utils", icon = painterResource("drawable/ic3.ico")
    ) {
        App()
    }
}

