package it.nlp.frontend.domain.reminder.usecase

import it.nlp.frontend.domain.reminder.model.GetReminderTimeFlowResult

interface GetReminderTimeFlowUseCase {
    suspend operator fun invoke(): GetReminderTimeFlowResult
}
