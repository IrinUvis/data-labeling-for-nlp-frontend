package it.winter2223.bachelor.ak.frontend.domain.reminder.model

sealed class StoreReminderTimeResult {
    object Success : StoreReminderTimeResult()

    object Failure : StoreReminderTimeResult()
}
