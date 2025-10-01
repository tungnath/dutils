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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import theme.GradientColors
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
            errorMessage = "Username doesn't exist"
            return false
        }

        val storedEmail = prefs.get("${user.lowercase(getDefault())}_em", null)
        val storedPass = prefs.get("${user.lowercase(getDefault())}_pwd", null)

        val isValid = storedEmail != null && !storedEmail.isBlank() && storedPass != null && storedPass == pass

        if (!isValid) errorMessage = "Invalid credentials"

        return isValid
    }

    Box(
        modifier = Modifier.fillMaxSize().background(GradientColors.gradientBackground)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(0.8f).fillMaxHeight(0.85f).align(Alignment.Center),
            elevation = 12.dp,
            shape = RoundedCornerShape(16.dp),
            backgroundColor = GradientColors.cardSurface
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
                            Box(
                                modifier = Modifier.size(60.dp).background(
                                    brush = GradientColors.primaryButton, shape = RoundedCornerShape(20.dp)
                                ), contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = null,
                                    tint = MaterialTheme.colors.primary,
                                    modifier = Modifier.size(56.dp)
                                )
                            }
                        }
                        Spacer(Modifier.height(8.dp))
                        Text(
                            "Login",
                            style = MaterialTheme.typography.h5,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colors.onSurface
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
                                    Icons.Default.Person, null, tint = GradientColors.primaryPurple
                                )
                            },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = GradientColors.primaryPurple,
                                focusedLabelColor = GradientColors.primaryPurple,
                                backgroundColor = Color.White.copy(alpha = 0.8f),
                                unfocusedBorderColor = Color.Gray.copy(alpha = 0.3f)
                            )
                        )
                        OutlinedTextField(
                            value = password,
                            onValueChange = { password = it; errorMessage = null },
                            label = { Text("Password") },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Lock, null, tint = GradientColors.primaryPurple
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
                                focusedBorderColor = GradientColors.primaryPurple,
                                focusedLabelColor = GradientColors.primaryPurple,
                                backgroundColor = Color.White.copy(alpha = 0.8f),
                                unfocusedBorderColor = Color.Gray.copy(alpha = 0.3f)
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
                            }, modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
//                            .height(56.dp)
                                .background(
                                    brush = GradientColors.primaryButton, shape = RoundedCornerShape(18.dp)
                                ), colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.Transparent
                            ), elevation = ButtonDefaults.elevation(0.dp), shape = RoundedCornerShape(18.dp)
                        ) {
                            Text(
                                "Login",
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold,
                                style = MaterialTheme.typography.button
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            TextButton(
                                onClick = onNavigateToSignup, colors = ButtonDefaults.textButtonColors(
                                    contentColor = GradientColors.primaryPurple.copy(alpha = 0.9f),
                                )
                            ) {
                                Text("Create Account")
                            }

                            TextButton(
                                onClick = onNavigateToForgotPassword, colors = ButtonDefaults.textButtonColors(
                                    contentColor = GradientColors.primaryPurple.copy(alpha = 0.9f),
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