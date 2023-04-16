package it.nlp.frontend.domain.reminder.usecase

import it.nlp.frontend.domain.reminder.model.CancelCommentLabelingRemindersResult

interface CancelCommentLabelingRemindersUseCase {
    operator fun invoke(): CancelCommentLabelingRemindersResult
}
