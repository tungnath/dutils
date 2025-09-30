import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import navigation.NavigationHost
import navigation.Screen
import navigation.rememberNavController
import theme.GradientTheme

@Composable
@Preview
fun App() {

    // Create navigation controller with Login as start destination
    val navController = rememberNavController(
        startDestination = Screen.Login.route
    )

    GradientTheme {
        // Use NavigationHost to handle routing
        NavigationHost(navController = navController)
    }


//    MaterialTheme {
//        // FileOperationsScreen()
//
////        LoginScreen(
////            { println("Callback On Login Success") },
////            { println("Callback To Navigate to Signup") },
////        ) {
////            println("Callback to Reset password")
////        }
//
////        SignUpScreen(
////            { println("Callback On SignUp Success") },
////            { println("Callback To Navigate to Login") },
////        ) {
////            println("Callback to Reset password")
////        }
//
////        ForgotPasswordScreen(
////            { println("Callback On Login Success") },
////            { println("Callback To Navigate to Login") },
////        ) {
////            println("Callback To Navigate to Signup")
////        }
//
//    }

}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication, title = "dUtils - Desktop Utils",
        icon = painterResource("drawable/ic3.ico")
    ) {
        App()
    }
}

