package it.nlp.frontend.domain.reminder.model

sealed class GetCommentLabelingReminderStatusResult {
    data class Success(val isScheduled: Boolean) : GetCommentLabelingReminderStatusResult()
}
