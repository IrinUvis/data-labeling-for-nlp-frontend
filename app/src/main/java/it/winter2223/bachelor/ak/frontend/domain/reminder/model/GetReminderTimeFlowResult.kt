package it.winter2223.bachelor.ak.frontend.domain.reminder.model

import kotlinx.coroutines.flow.Flow

sealed class GetReminderTimeFlowResult {
    data class Success(val reminderTimeFlow: Flow<ReminderTime?>) : GetReminderTimeFlowResult()

    object Failure : GetReminderTimeFlowResult()
}
