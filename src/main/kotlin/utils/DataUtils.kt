package utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import screens.NotificationItem

object DataUtils {

    val allNotifications = listOf(
        NotificationItem(
            id = "1",
            title = "File Operation",
            subtitle = "Successfully renamed 5 files",
            icon = Icons.Default.CheckCircle,
            timestamp = "2024-10-31",
            isRead = true
        ), NotificationItem(
            id = "2",
            title = "System Update",
            subtitle = "New version available",
            icon = Icons.Default.Info,
            timestamp = "2024-10-30",
            isRead = true
        ), NotificationItem(
            id = "3",
            title = "Error Alert",
            subtitle = "Failed to process one file",
            icon = Icons.Default.Error,
            timestamp = "2024-10-29",
            isRead = false
        ), NotificationItem(
            id = "4",
            title = "Task Completed",
            subtitle = "Batch operation finished",
            icon = Icons.Default.Done,
            timestamp = "2024-10-28",
            isRead = true
        ), NotificationItem(
            id = "5",
            title = "User Activity",
            subtitle = "Login from new device",
            icon = Icons.Default.Person,
            timestamp = "2024-10-27",
            isRead = true
        )
    )

    val areNotificationsPending: Boolean = allNotifications.any { !it.isRead }

}