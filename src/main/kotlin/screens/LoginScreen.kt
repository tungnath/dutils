package screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import java.util.Locale.getDefault
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

        if (user.isBlank() || pass.isBlank()) {
            errorMessage = "All fields are required"
            return false
        }

        if (pass.length < 6) {
            errorMessage = "Password must be at least 6 characters"
            return false
        }

        if (!prefs.keys().contains(user)) {
            errorMessage = "Username doesn't exists"
            return false
        }

        val storedEmail = prefs.get("${user.lowercase(getDefault())}_em", null)
        val storedPass = prefs.get("${user.lowercase(getDefault())}_pwd", null)

        val isValid = storedEmail != null && !storedEmail.isBlank() && storedPass != null && storedPass == pass

        if (!isValid) errorMessage = "Invalid credentials"

        return isValid
    }

    Box(
        modifier = Modifier.fillMaxSize().background(
            Brush.verticalGradient(
                listOf(
                    MaterialTheme.colors.primary.copy(alpha = 0.3f),
                    MaterialTheme.colors.background,
                    MaterialTheme.colors.secondary.copy(alpha = 0.3f)
                )
            )
        )
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(0.8f).fillMaxHeight(0.85f).align(Alignment.Center),
            elevation = 12.dp,
            shape = RoundedCornerShape(16.dp),
            backgroundColor = MaterialTheme.colors.surface
        ) {
            Column(modifier = Modifier.fillMaxSize()) {

                Box(
                    modifier = Modifier.fillMaxWidth().weight(0.25f), contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        val resource = remember {
                            Thread.currentThread().contextClassLoader.getResource("drawable/ic2.png")
                        }
                        if (resource != null) {
                            Image(
                                painter = painterResource("drawable/ic2.png"),
                                contentDescription = null,
                                modifier = Modifier.size(56.dp).clip(RoundedCornerShape(12.dp))
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                tint = MaterialTheme.colors.primary,
                                modifier = Modifier.size(56.dp)
                            )
                        }
                        Spacer(Modifier.height(8.dp))
                        Text(
                            "Login", style = MaterialTheme.typography.h5, fontWeight = FontWeight.Bold
                        )
                    }
                }


                Box(
                    modifier = Modifier.fillMaxWidth().weight(0.75f),
                    contentAlignment = Alignment.Center,
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(0.8f),
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        OutlinedTextField(
                            value = username,
                            onValueChange = { username = it; errorMessage = null },
                            label = { Text("Username") },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Person, null, tint = MaterialTheme.colors.primary
                                )
                            },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = MaterialTheme.colors.primary,
                                focusedLabelColor = MaterialTheme.colors.primary
                            )
                        )
                        OutlinedTextField(
                            value = password,
                            onValueChange = { password = it; errorMessage = null },
                            label = { Text("Password") },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Lock, null, tint = MaterialTheme.colors.primary
                                )
                            },
                            visualTransformation = PasswordVisualTransformation(),
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    if (validateCredentials(
                                            username, password
                                        )
                                    ) onLoginSuccess()
                                }),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = MaterialTheme.colors.primary,
                                focusedLabelColor = MaterialTheme.colors.primary
                            )
                        )


                        errorMessage?.let {
                            Text(
                                it, color = MaterialTheme.colors.error, modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                        Button(
                            onClick = {
                                if (validateCredentials(username, password)) onLoginSuccess()
                            }, modifier = Modifier.fillMaxWidth().padding(top = 8.dp), shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Login")
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            TextButton(
                                onClick = onNavigateToSignup, colors = ButtonDefaults.textButtonColors(
                                    contentColor = MaterialTheme.colors.primary
                                )
                            ) {
                                Text("Doesn't Have Account?")
                            }

                            TextButton(
                                onClick = onNavigateToForgotPassword, colors = ButtonDefaults.textButtonColors(
                                    contentColor = MaterialTheme.colors.primary
                                )
                            ) {
                                Text("Forgot Password?")
                            }
                        }

                    }

                }

            }
        }
    }

}