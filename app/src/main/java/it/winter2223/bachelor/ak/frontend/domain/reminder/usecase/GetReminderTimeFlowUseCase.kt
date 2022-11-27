package it.winter2223.bachelor.ak.frontend.domain.reminder.usecase

import it.winter2223.bachelor.ak.frontend.domain.reminder.model.GetReminderTimeFlowResult

interface GetReminderTimeFlowUseCase {
    suspend operator fun invoke(): GetReminderTimeFlowResult
}
