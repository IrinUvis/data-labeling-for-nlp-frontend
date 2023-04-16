package it.nlp.frontend.data.local.reminder.repository

import it.nlp.frontend.data.local.reminder.model.ReminderTimePreferences
import kotlinx.coroutines.flow.Flow

interface ReminderTimeRepository {
    suspend fun storeReminderTime(reminderTimePreferences: ReminderTimePreferences)

    suspend fun clearReminderTime()

    suspend fun reminderTimeFlow(): Flow<ReminderTimePreferences?>
}
