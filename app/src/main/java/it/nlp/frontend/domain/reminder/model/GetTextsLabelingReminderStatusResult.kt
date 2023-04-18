package it.nlp.frontend.domain.reminder.model

sealed class GetTextsLabelingReminderStatusResult {
    data class Success(val isScheduled: Boolean) : GetTextsLabelingReminderStatusResult()
}
