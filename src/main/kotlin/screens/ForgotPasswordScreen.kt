package screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import theme.GradientColors
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
        if (!prefs.keys().contains(user)) {
            message = "User doesn't exist"
            return false
        }

        val storedUser = prefs.get(user, null)
        val isValidUser = storedUser != null && storedUser.isNotBlank()

        // set the message as well.
        if (isValidUser) {
            val newPassword = randomPassword(user)

            // Update the preferences
            prefs.put("${user.lowercase(getDefault())}_pwd", newPassword)

            message = "Your new password is $newPassword"
        } else {
            message = "Invalid user"
        }

        return isValidUser
    }


    Box(
        modifier = Modifier.fillMaxSize().background(
            GradientColors.gradientBackground
        )
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
                            "Forgot Password",
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
                            onValueChange = { username = it; message = null },
                            label = { Text("Username") },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Person, null, tint = GradientColors.primaryPurple
                                )
                            },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    if (validateCredentials(
                                            username
                                        )
                                    ) onForgotPasswordSuccess()
                                }),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = GradientColors.primaryPurple,
                                focusedLabelColor = GradientColors.primaryPurple,
                                backgroundColor = Color.White.copy(alpha = 0.8f),
                                unfocusedBorderColor = Color.Gray.copy(alpha = 0.3f)
                            )
                        )


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
                                    print("Password changed")
//                                    onForgotPasswordSuccess()
                                }
                            }, modifier = Modifier.fillMaxWidth().padding(top = 8.dp)

                                //                            .height(56.dp)
                                .background(
                                    brush = GradientColors.primaryButton, shape = RoundedCornerShape(18.dp)
                                ), colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.Transparent
                            ), elevation = ButtonDefaults.elevation(0.dp),

                            shape = RoundedCornerShape(18.dp)
                        ) {
                            Text(
                                "Reset Password",
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold,
                                style = MaterialTheme.typography.button
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            TextButton(
                                onClick = onNavigateToLogin, colors = ButtonDefaults.textButtonColors(
                                    contentColor = GradientColors.primaryPurple.copy(alpha = 0.9f),
                                )
                            ) {
                                Text("Back to Login")
                            }

                            TextButton(
                                onClick = onNavigateToSignup, colors = ButtonDefaults.textButtonColors(
                                    contentColor = GradientColors.primaryPurple.copy(alpha = 0.9f),
                                )
                            ) {
                                Text("Create Account")
                            }
                        }

                    }

                }

            }
        }
    }

}