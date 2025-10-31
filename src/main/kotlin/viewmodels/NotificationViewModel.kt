package viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

class NotificationViewModel {
    private val _readNotifications = mutableStateOf<Set<String>>(emptySet())
    val readNotifications: State<Set<String>> = _readNotifications

    fun markAsRead(notificationId: String) {
        _readNotifications.value = _readNotifications.value + notificationId
    }
}