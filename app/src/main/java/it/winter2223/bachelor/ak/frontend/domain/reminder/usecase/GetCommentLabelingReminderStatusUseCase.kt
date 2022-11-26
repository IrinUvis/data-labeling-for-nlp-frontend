package it.winter2223.bachelor.ak.frontend.domain.reminder.usecase

import it.winter2223.bachelor.ak.frontend.domain.reminder.model.GetCommentLabelingReminderStatusResult

interface GetCommentLabelingReminderStatusUseCase {
    operator fun invoke(): GetCommentLabelingReminderStatusResult
}
