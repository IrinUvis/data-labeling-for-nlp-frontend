package it.winter2223.bachelor.ak.frontend.domain.reminder.usecase

import it.winter2223.bachelor.ak.frontend.domain.reminder.model.ReminderTime
import it.winter2223.bachelor.ak.frontend.domain.reminder.model.StoreReminderTimeResult

interface StoreReminderTimeUseCase {
    suspend operator fun invoke(reminderTime: ReminderTime): StoreReminderTimeResult
}
