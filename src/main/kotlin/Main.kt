import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import screens.FileOperationsScreen
import screens.LoginScreen
import screens.SignUpScreen

@Composable
@Preview
fun App() {
    MaterialTheme {
        // FileOperationsScreen()

        LoginScreen(
            { println("Callback On Login Success") },
            { println("Callback To Navigate to Signup") },
        ) {
            println("Callback to Reset password")
        }

//        SignUpScreen(
//            { println("Callback On SignUp Success") },
//            { println("Callback To Navigate to Login") },
//        ) {
//            println("Callback to Reset password")
//        }

    }
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication, title = "dUtils - Desktop Utils"
    ) {
        App()
    }
}
