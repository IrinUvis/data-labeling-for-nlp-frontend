package it.nlp.frontend.domain.reminder.usecase

import it.nlp.frontend.domain.reminder.model.GetCommentLabelingReminderStatusResult

interface GetCommentLabelingReminderStatusUseCase {
    operator fun invoke(): GetCommentLabelingReminderStatusResult
}
