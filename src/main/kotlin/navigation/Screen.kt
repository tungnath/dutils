package navigation

/**
 * Sealed class to define all navigation routes in the app
 */
sealed class Screen(val route: String) {
    object Login : Screen("login")
    object SignUp : Screen("signup")
    object ForgotPassword : Screen("forgot_password")
    object Home : Screen("home")
    object FileOperations : Screen("file_operations")
    object Notifications : Screen("notifications")
}