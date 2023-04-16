package it.nlp.frontend.data.local.reminder.repository.impl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import it.nlp.frontend.data.local.reminder.model.ReminderTimePreferences
import it.nlp.frontend.data.local.reminder.repository.ReminderTimeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreReminderTimeRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : ReminderTimeRepository {
    companion object {
        private object ReminderTimePreferencesKeys {
            val HOUR = intPreferencesKey("hour")
            val MINUTE = intPreferencesKey("minute")
        }
    }

    override suspend fun storeReminderTime(reminderTimePreferences: ReminderTimePreferences) {
        dataStore.edit { preferences ->
            preferences[ReminderTimePreferencesKeys.HOUR] = reminderTimePreferences.hour
            preferences[ReminderTimePreferencesKeys.MINUTE] = reminderTimePreferences.minute
        }
    }

    override suspend fun clearReminderTime() {
        dataStore.edit { preferences ->
            preferences.remove(ReminderTimePreferencesKeys.HOUR)
            preferences.remove(ReminderTimePreferencesKeys.MINUTE)
        }
    }

    override suspend fun reminderTimeFlow(): Flow<ReminderTimePreferences?> {
        return dataStore.data.map { preferences ->
            val hour = preferences[ReminderTimePreferencesKeys.HOUR]
            val minute = preferences[ReminderTimePreferencesKeys.MINUTE]

            if (hour == null || minute == null) {
                null
            } else {
                ReminderTimePreferences(
                    hour = hour,
                    minute = minute,
                )
            }
        }
    }
}
