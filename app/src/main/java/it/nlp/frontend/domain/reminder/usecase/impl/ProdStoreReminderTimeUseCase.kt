package it.nlp.frontend.domain.reminder.usecase.impl

import it.nlp.frontend.data.local.reminder.model.ReminderTimePreferences
import it.nlp.frontend.data.local.reminder.repository.ReminderTimeRepository
import it.nlp.frontend.domain.reminder.model.ReminderTime
import it.nlp.frontend.domain.reminder.model.StoreReminderTimeResult
import it.nlp.frontend.domain.reminder.usecase.StoreReminderTimeUseCase
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
