package screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(onNavigateToFileOps: () -> Unit, onLogout: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Home Screen", style = MaterialTheme.typography.h4
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onNavigateToFileOps, modifier = Modifier.fillMaxWidth(0.5f)
        ) {
            Text("File Name Operations")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onLogout, modifier = Modifier.fillMaxWidth(0.5f)
        ) {
            Text("Logout")
        }
    }
}