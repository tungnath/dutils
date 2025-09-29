package screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import utils.CommonUtils.isValidEmail
import java.util.Locale.getDefault
import java.util.prefs.Preferences

@Composable
fun SignUpScreen(
    onSignUpSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onNavigateToForgotPassword: () -> Unit
) {
    // State variables
    var emailId by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val prefs = Preferences.userRoot().node("dUtils")

    fun validateDetails(email: String, user: String, pass: String): Boolean {
        if (email.isBlank() || user.isBlank() || pass.isBlank()) {
            errorMessage = "All fields are required"
            return false
        }
        if (!isValidEmail(email)) {
            errorMessage = "Please enter a valid email address"
            return false
        }
        if (pass.length < 6) {
            errorMessage = "Password must be at least 6 characters"
            return false
        }
        // Check if user already exists
        val allKeys = prefs.keys()
        if (allKeys.contains(user)) {
            errorMessage = "Username already exists"
            return false
        }
        // Save details to preferences
        prefs.put(user, user)
        prefs.put("${user.lowercase(getDefault())}_em", email)
        prefs.put("${user.lowercase(getDefault())}_pwd", pass)
        return true
    }

    // Modern gradient background
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colors.primary.copy(alpha = 0.3f),
                        MaterialTheme.colors.secondaryVariant.copy(alpha = 0.3f)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(18.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(fraction = 0.7f)
                    .fillMaxHeight(fraction = 0.9f),
                elevation = 16.dp,
                backgroundColor = MaterialTheme.colors.surface,
                shape = RoundedCornerShape(24.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp)
                ) {
                    // TOP SECTION (30% - 0.3f) - Icon and Title
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(fraction = 0.3f),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // App icon
                        val iconResource = remember {
                            Thread.currentThread().contextClassLoader
                                .getResource("drawable/ic2.png")?.toString()
                        }

                        if (iconResource != null) {
                            Image(
                                painter = painterResource("drawable/ic2.png"),
                                contentDescription = "App Icon",
                                modifier = Modifier
                                    .size(64.dp)
                                    .clip(RoundedCornerShape(16.dp))
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "App Icon",
                                modifier = Modifier.size(64.dp),
                                tint = MaterialTheme.colors.primary
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Title
                        Text(
                            text = "Create Account",
                            style = MaterialTheme.typography.h5,
                            color = MaterialTheme.colors.onSurface,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )

                        Text(
                            text = "Join dUTILS today",
                            style = MaterialTheme.typography.subtitle2,
                            color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f),
                            textAlign = TextAlign.Center
                        )
                    }

                    // MIDDLE SECTION (45% - 0.45f) - Form Fields
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(fraction = 0.45f), // Adjusted fraction for remaining space
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            // Email field
                            OutlinedTextField(
                                value = emailId,
                                onValueChange = {
                                    emailId = it
                                    errorMessage = null
                                },
                                label = { Text("Email Address") },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Email,
                                        contentDescription = null,
                                        tint = MaterialTheme.colors.primary
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

                            // Username field
                            OutlinedTextField(
                                value = username,
                                onValueChange = {
                                    username = it
                                    errorMessage = null
                                },
                                label = { Text("Username") },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Person,
                                        contentDescription = null,
                                        tint = MaterialTheme.colors.primary
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

                            // Password field
                            OutlinedTextField(
                                value = password,
                                onValueChange = {
                                    password = it
                                    errorMessage = null
                                },
                                label = { Text("Password") },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Lock,
                                        contentDescription = null,
                                        tint = MaterialTheme.colors.primary
                                    )
                                },
                                singleLine = true,
                                visualTransformation = PasswordVisualTransformation(),
                                modifier = Modifier.fillMaxWidth(),
                                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                                keyboardActions = KeyboardActions(
                                    onDone = {
                                        if (validateDetails(emailId, username, password)) {
                                            onSignUpSuccess()
                                        }
                                    }
                                ),
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    focusedBorderColor = MaterialTheme.colors.primary,
                                    focusedLabelColor = MaterialTheme.colors.primary
                                )
                            )
                        }
                    }

                    // BOTTOM SECTION (25% - 0.25f) - Error, Button, Navigation
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(fraction = 0.25f), // Takes remaining space (25%)
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Error message
                        errorMessage?.let { message ->
                            Card(
                                backgroundColor = MaterialTheme.colors.error.copy(alpha = 0.1f),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 16.dp)
                            ) {
                                Text(
                                    text = message,
                                    color = MaterialTheme.colors.error,
                                    style = MaterialTheme.typography.body2,
                                    modifier = Modifier.padding(12.dp),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }

                        // Sign Up button
                        Button(
                            onClick = {
                                if (validateDetails(emailId, username, password)) {
                                    onSignUpSuccess()
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = MaterialTheme.colors.primary
                            )
                        ) {
                            Text(
                                text = "Create Account",
                                style = MaterialTheme.typography.button,
                                color = MaterialTheme.colors.onPrimary,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Navigation links
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TextButton(
                                onClick = onNavigateToLogin,
                                colors = ButtonDefaults.textButtonColors(
                                    contentColor = MaterialTheme.colors.primary
                                )
                            ) {
                                Text("Already have account?")
                            }

                            TextButton(
                                onClick = onNavigateToForgotPassword,
                                colors = ButtonDefaults.textButtonColors(
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