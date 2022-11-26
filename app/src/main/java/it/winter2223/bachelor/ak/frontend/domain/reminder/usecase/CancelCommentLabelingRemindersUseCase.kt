package it.winter2223.bachelor.ak.frontend.domain.reminder.usecase

import it.winter2223.bachelor.ak.frontend.domain.reminder.model.CancelCommentLabelingRemindersResult

interface CancelCommentLabelingRemindersUseCase {
    operator fun invoke(): CancelCommentLabelingRemindersResult
}
