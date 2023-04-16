package it.nlp.frontend.domain.reminder.usecase

import it.nlp.frontend.domain.reminder.model.ReminderTime
import it.nlp.frontend.domain.reminder.model.StoreReminderTimeResult

interface StoreReminderTimeUseCase {
    suspend operator fun invoke(reminderTime: ReminderTime): StoreReminderTimeResult
}
