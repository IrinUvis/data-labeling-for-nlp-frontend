package it.winter2223.bachelor.ak.frontend.ui.settings.model

private const val DEFAULT_HOUR_OF_REMINDERS = 20
private const val DEFAULT_MINUTE_OF_REMINDERS = 0

data class RemindersState(
    val turnOn: Boolean,
    val scheduledHourOfDay: Int = DEFAULT_HOUR_OF_REMINDERS,
    val scheduledMinute: Int = DEFAULT_MINUTE_OF_REMINDERS,
    val selectedHourOfDay: Int = scheduledHourOfDay,
    val selectedMinute: Int = scheduledMinute,
)
