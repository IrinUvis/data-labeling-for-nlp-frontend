package it.nlp.frontend.domain.reminder.model

import kotlinx.coroutines.flow.Flow

sealed class GetReminderTimeFlowResult {
    data class Success(val reminderTimeFlow: Flow<ReminderTime?>) : GetReminderTimeFlowResult()

    data class Failure(val e: Exception) : GetReminderTimeFlowResult()
}
