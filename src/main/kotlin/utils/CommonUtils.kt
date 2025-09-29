package utils

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

object CommonUtils {

    @Composable
    fun isDarkTheme(): Boolean {
        // Parameterize and check for other resources like colors, shapes, typography
        return !MaterialTheme.colors.isLight
    }


    fun randomPassword(user: String): String {
        return "$user@${(Math.random() * 100000).toInt()}"
    }

    fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
        return email.matches(emailRegex.toRegex())
    }

//    fun randomPassword(username: String): String {
//        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
//        val random = Random(username.hashCode()) // Seed with username for consistency
//        return (1..8)
//            .map { chars.random(random) }
//            .joinToString("")
//    }



}