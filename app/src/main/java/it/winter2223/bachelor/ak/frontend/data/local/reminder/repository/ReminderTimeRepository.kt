package it.winter2223.bachelor.ak.frontend.data.local.reminder.repository

import it.winter2223.bachelor.ak.frontend.data.local.reminder.model.ReminderTimePreferences
import kotlinx.coroutines.flow.Flow

interface ReminderTimeRepository {
    suspend fun storeReminderTime(reminderTimePreferences: ReminderTimePreferences)

    suspend fun clearReminderTime()

    suspend fun reminderTimeFlow(): Flow<ReminderTimePreferences?>
}
