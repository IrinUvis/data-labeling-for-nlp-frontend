package it.nlp.frontend.ui.settings.model

private const val DEFAULT_HOUR_OF_REMINDERS = 20
private const val DEFAULT_MINUTE_OF_REMINDERS = 0

data class UiReminderTime(
    val hourOfDay: Int = DEFAULT_HOUR_OF_REMINDERS,
    val minute: Int = DEFAULT_MINUTE_OF_REMINDERS,
)
