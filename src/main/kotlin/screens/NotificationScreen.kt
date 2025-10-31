package screens


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import utils.DataUtils
import viewmodels.NotificationViewModel

// Data class for notifications
data class NotificationItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val timestamp: String,
    var isRead: Boolean = false
)


@Composable
fun NotificationScreen(viewModel: NotificationViewModel, onNavigateUpToHomeScreen: () -> Unit) {
    val scaffoldState = rememberScaffoldState()
    val readIds by viewModel.readNotifications
    var searchQuery by remember { mutableStateOf("") }
    var selectedItem by remember { mutableStateOf<NotificationItem?>(null) }

    // sample data
    val allNotifications = DataUtils.allNotifications

    // Filter notifications based on a search query
    val filteredNotifications = remember(searchQuery) {
        if (searchQuery.isEmpty()) {
            allNotifications
        } else {
            allNotifications.filter {
                it.title.contains(searchQuery, ignoreCase = true) || it.subtitle.contains(
                    searchQuery, ignoreCase = true
                )
            }
        }
    }


    Scaffold(
        scaffoldState = scaffoldState, topBar = {
            TopAppBar(
                title = {
                Text(
                    text = "Notifications",
                    style = MaterialTheme.typography.h5,
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
            }, navigationIcon = {
                IconButton(onClick = {
                    onNavigateUpToHomeScreen()
                }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack, contentDescription = "Back"
                    )
                }
            }, backgroundColor = MaterialTheme.colors.primary
            )
        }) { paddingValues ->

        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {
            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search notifications") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                modifier = Modifier.fillMaxWidth().height(48.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFF1565C0), focusedLabelColor = Color(0xFF1565C0)
                ),
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Notifications List
            if (filteredNotifications.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (searchQuery.isEmpty()) "No notifications"
                        else "No notifications found", color = Color.Gray, style = MaterialTheme.typography.body1
                    )
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(filteredNotifications) { notification ->
                        val isRead = notification.id in readIds

                        NotificationCard(notification = notification.copy(isRead = isRead)) {
                            viewModel.markAsRead(notification.id)
                            selectedItem = notification    // <-- set when clicked
                        }

                    }
                }
            }


            if (selectedItem != null) {
                AlertDialog(
                    onDismissRequest = { selectedItem = null },
                    title = { Text(text = selectedItem!!.title) },
                    text = {
                        Text(
                            text = selectedItem!!.title + "\n" + selectedItem!!.subtitle + "\n" + selectedItem!!.timestamp
                        )
                    },
                    confirmButton = {
                        TextButton(onClick = { selectedItem = null }) {
                            Text("OK")
                        }
                    })
            }

        }

    }


}

@Composable
private fun NotificationCard(
    notification: NotificationItem, onClick: () -> Unit = { }
) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() }.height(80.dp),
        elevation = if (notification.isRead) 0.dp else 4.dp,
        shape = RoundedCornerShape(12.dp),
        backgroundColor = if (notification.isRead) Color(0xFFF5F5F5)
        else Color(0xFFE3F2FD)
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon Container
            Box(
                modifier = Modifier.size(48.dp).background(
                    color = if (notification.isRead) Color(0xFFE0E0E0)
                    else Color(0xFF1565C0), shape = RoundedCornerShape(8.dp)
                ), contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = notification.icon,
                    contentDescription = null,
                    tint = if (notification.isRead) Color.Gray
                    else Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }

            // Content
            Column(
                modifier = Modifier.weight(1f).fillMaxHeight(), verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = notification.title,
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF333333)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = notification.subtitle,
                    style = MaterialTheme.typography.body2,
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }

            // Timestamp
            Text(
                text = notification.timestamp,
                style = MaterialTheme.typography.caption,
                color = Color.LightGray,
                fontSize = 11.sp,
                modifier = Modifier.align(Alignment.Top)
            )
        }
    }
}