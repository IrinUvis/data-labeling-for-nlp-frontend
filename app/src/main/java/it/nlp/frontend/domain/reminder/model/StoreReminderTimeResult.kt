package it.nlp.frontend.domain.reminder.model

sealed class StoreReminderTimeResult {
    object Success : StoreReminderTimeResult()

    data class Failure(val e: Exception) : StoreReminderTimeResult()
}
