package it.winter2223.bachelor.ak.frontend.domain.reminder.usecase.impl

import it.winter2223.bachelor.ak.frontend.data.local.reminder.model.ReminderTimePreferences
import it.winter2223.bachelor.ak.frontend.data.local.reminder.repository.ReminderTimeRepository
import it.winter2223.bachelor.ak.frontend.domain.reminder.model.ReminderTime
import it.winter2223.bachelor.ak.frontend.domain.reminder.model.StoreReminderTimeResult
import it.winter2223.bachelor.ak.frontend.domain.reminder.usecase.StoreReminderTimeUseCase
import java.io.IOException
import javax.inject.Inject

class ProdStoreReminderTimeUseCase @Inject constructor(
    private val reminderTimeRepository: ReminderTimeRepository,
) : StoreReminderTimeUseCase {
    override suspend fun invoke(reminderTime: ReminderTime): StoreReminderTimeResult {
        return try {
            reminderTimeRepository.storeReminderTime(
                ReminderTimePreferences(
                    hour = reminderTime.hour,
                    minute = reminderTime.minute,
                ),
            )
            StoreReminderTimeResult.Success
        } catch (e: IOException) {
            StoreReminderTimeResult.Failure(e)
        }
    }
}
