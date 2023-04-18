package it.nlp.frontend.domain.reminder.usecase

import it.nlp.frontend.domain.reminder.model.GetTextsLabelingReminderStatusResult

interface GetTextsLabelingReminderStatusUseCase {
    operator fun invoke(): GetTextsLabelingReminderStatusResult
}
