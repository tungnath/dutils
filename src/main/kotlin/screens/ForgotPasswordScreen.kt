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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import utils.CommonUtils.randomPassword
import java.util.Locale.getDefault
import java.util.prefs.Preferences

@Composable
fun ForgotPasswordScreen(
    onForgotPasswordSuccess: () -> Unit, onNavigateToLogin: () -> Unit, onNavigateToSignup: () -> Unit
) {
    // State vars
    var username by remember { mutableStateOf("") }
    var message by remember { mutableStateOf<String?>(null) }
    val prefs = Preferences.userRoot().node("dUtils")

    fun validateCredentials(user: String): Boolean {
        if (!prefs.keys().contains(user)) return false

        val storedUser = prefs.get(user, null)
        val isValidUser = storedUser != null && storedUser.isNotBlank()

        // set the message as well.
        if (isValidUser) {
            val newPassword = randomPassword(user)

            // Update the preferences
            prefs.put("${user.lowercase(getDefault())}_pwd", newPassword)

            message = "Your new password is $newPassword"
        }

        return isValidUser
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Card(
            modifier = Modifier.fillMaxWidth(fraction = 0.7f).fillMaxHeight(fraction = 0.85f)
//                .wrapContentHeight()
                .padding(8.dp), elevation = 16.dp, shape = RoundedCornerShape(12.dp)
        ) {

            Column(
                modifier = Modifier.fillMaxSize().padding(18.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Forgot Password",
                    style = MaterialTheme.typography.h4,
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )

                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it; message = null },
                    label = { Text("Username") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(0.6f),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            if (validateCredentials(username)) onForgotPasswordSuccess()
                            else message = "Invalid credentials"
                        })
                )

//                message?.let {
//                    Text(
//                        text = it, color = MaterialTheme.colors.error, modifier = Modifier.padding(top = 8.dp)
//                    )
//                }

                message?.let { msg ->
                    Text(
                        text = msg, color = if (msg.startsWith("Your new password")) {
                            MaterialTheme.colors.primary  // success message
                        } else {
                            MaterialTheme.colors.error     // error message
                        }, modifier = Modifier.padding(top = 8.dp)
                    )
                }

                Button(
                    onClick = {
                        if (validateCredentials(username)) {
                            onForgotPasswordSuccess()
                        } else {
                            message = "Invalid credentials"
                        }
                    }, modifier = Modifier.fillMaxWidth(0.6f).padding(top = 16.dp)
                ) {
                    Text("Reset", modifier = Modifier.padding(4.dp))
                }

                Row(
                    modifier = Modifier.padding(top = 8.dp).fillMaxWidth(0.6f),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(onClick = onNavigateToLogin) { Text("Login") }
                    TextButton(onClick = onNavigateToSignup) { Text("Sign Up") }
                }
            }

        }
    }

}