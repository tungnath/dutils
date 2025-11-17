package navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import composables.NotificationScreen
import screens.*
import viewmodels.NotificationViewModel

/**
 * Navigation Host that handles routing between different screens
 */
@Composable
fun NavigationHost(navController: NavController) {
    // Get current screen
    val currentScreen by navController.currentScreen

    // Navigate based on the current route
    when (currentScreen) {
        Screen.Login.route -> {
            LoginScreen(onLoginSuccess = {
                navController.navigate(Screen.Home.route)
            }, onNavigateToSignup = {
                navController.navigate(Screen.SignUp.route)
            }, onNavigateToForgotPassword = {
                navController.navigate(Screen.ForgotPassword.route)
            })
        }

        Screen.SignUp.route -> {
            SignUpScreen(onSignUpSuccess = {
                navController.navigate(Screen.Login.route)
            }, onNavigateToLogin = {
                navController.navigate(Screen.Login.route)
            }, onNavigateToForgotPassword = {
                navController.navigate(Screen.ForgotPassword.route)
            })
        }

        Screen.ForgotPassword.route -> {
            ForgotPasswordScreen(onForgotPasswordSuccess = {
                navController.navigate(Screen.Login.route)
            }, onNavigateToLogin = {
                navController.navigate(Screen.Login.route)
            }, onNavigateToSignup = {
                navController.navigate(Screen.SignUp.route)
            })
        }

        Screen.Home.route -> {
            HomeScreen(
                onNavigateToFileOps = {
                    navController.navigate(Screen.FileOperations.route)
                },
                onNavigateToNotifications = {
                    navController.navigate(Screen.Notifications.route)
                },
                onLogout = {
                    navController.navigate(Screen.Login.route) // navigateToStart()
                })
        }

        Screen.FileOperations.route -> {
            FileOperationsScreen(
                onNavigateUpToHomeScreen = {
                    navController.navigate(Screen.Home.route)
                }
            )
        }

        Screen.Notifications.route -> {
            NotificationScreen(
                NotificationViewModel(),
                onNavigateUpToHomeScreen = {
                    navController.navigate(Screen.Home.route)
                }
            )
        }

    }
}

