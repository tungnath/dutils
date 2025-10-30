package screens

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
@Preview
fun NotificationScreen() {
    var searchQuery by remember { mutableStateOf("") }
    val items = listOf(
        "Apple", "Banana", "Orange", "Grapes", "Mango", "Cherry", "Watermelon", "Blueberry", "Peach"
    )

    val filteredItems = remember(searchQuery) {
        if (searchQuery.isEmpty()) items
        else items.filter { it.contains(searchQuery, ignoreCase = true) }
    }

    Column(modifier = Modifier.padding(16.dp).fillMaxSize()) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default,
            keyboardActions = KeyboardActions.Default
        )

        Spacer(modifier = Modifier.height(16.dp))

        Column(modifier = Modifier.fillMaxWidth()) {
            filteredItems.forEach { item ->
                Text(
                    text = item, style = MaterialTheme.typography.h5, modifier = Modifier.padding(4.dp)
                )
            }
            if (filteredItems.isEmpty()) {
                Text(text = "No items found", modifier = Modifier.padding(4.dp))
            }
        }
    }
}