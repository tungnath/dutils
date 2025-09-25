package screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import java.util.prefs.Preferences

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit, onNavigateToSignup: () -> Unit, onNavigateToForgotPassword: () -> Unit
) {
    // State vars
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val prefs = Preferences.userRoot().node("dUtils")

    fun validateCredentials(user: String, pass: String): Boolean {

        if (!prefs.keys().contains(user)) return false




        val storedPass = prefs.get(user, null)
        return storedPass != null && storedPass == pass
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Card(
            modifier = Modifier.fillMaxWidth(fraction = 0.7f)
                .fillMaxHeight(fraction = 0.85f)
//                .wrapContentHeight()
                .padding(8.dp),
            elevation = 16.dp,
            shape = RoundedCornerShape(12.dp)
        ) {

            Column(
                modifier = Modifier.fillMaxSize().padding(18.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Login",
                    style = MaterialTheme.typography.h4,
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )

                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it; errorMessage = null },
                    label = { Text("Username") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(0.6f),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it; errorMessage = null },
                    label = { Text("Password") },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(0.6f).padding(top = 8.dp),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            if (validateCredentials(username, password)) onLoginSuccess()
                            else errorMessage = "Invalid credentials"
                        })
                )

                errorMessage?.let {
                    Text(
                        text = it, color = MaterialTheme.colors.error, modifier = Modifier.padding(top = 8.dp)
                    )
                }

                Button(
                    onClick = {
                        if (validateCredentials(username, password)) {
                            onLoginSuccess()
                        } else {
                            errorMessage = "Invalid credentials"
                        }
                    }, modifier = Modifier.fillMaxWidth(0.6f).padding(top = 16.dp)
                ) {
                    Text("Login", modifier = Modifier.padding( 4.dp))
                }

                Row(
                    modifier = Modifier.padding(top = 8.dp).fillMaxWidth(0.6f),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(onClick = onNavigateToSignup) { Text("Sign Up") }
                    TextButton(onClick = onNavigateToForgotPassword) { Text("Forgot Password?") }
                }
            }


        }
    }

}