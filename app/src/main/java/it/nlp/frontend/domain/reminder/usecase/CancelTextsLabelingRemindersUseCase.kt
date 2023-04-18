package it.nlp.frontend.domain.reminder.usecase

import it.nlp.frontend.domain.reminder.model.CancelTextsLabelingRemindersResult

interface CancelTextsLabelingRemindersUseCase {
    operator fun invoke(): CancelTextsLabelingRemindersResult
}
