package it.winter2223.bachelor.ak.frontend.ui.settings.model

sealed class NotificationState {
    object Disabled : NotificationState()

    data class Enabled(
        val hour: Int,
        val minute: Int,
    ) : NotificationState()
}
