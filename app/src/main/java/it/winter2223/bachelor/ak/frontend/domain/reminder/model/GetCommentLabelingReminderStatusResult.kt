package it.winter2223.bachelor.ak.frontend.domain.reminder.model

sealed class GetCommentLabelingReminderStatusResult {
    data class Success(val isScheduled: Boolean) : GetCommentLabelingReminderStatusResult()
}
