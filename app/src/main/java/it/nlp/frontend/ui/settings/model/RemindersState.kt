package it.nlp.frontend.ui.settings.model

data class RemindersState(
    val turnOn: Boolean,
    val scheduledReminderTime: UiReminderTime,
    val selectedReminderTime: UiReminderTime,
)
