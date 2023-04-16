package it.nlp.frontend.data.local.reminder.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import it.winter2223.bachelor.ak.frontend.data.local.reminder.model.ReminderTimePreferences
import it.winter2223.bachelor.ak.frontend.data.local.reminder.repository.ReminderTimeRepository
import it.winter2223.bachelor.ak.frontend.data.local.reminder.repository.impl.DataStoreReminderTimeRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class ReminderTimeRepositoryTest {
    companion object {
        private const val TEST_DATASTORE_NAME = "test_datastore_reminder"
    }

    private val testCoroutineDispatcher: TestDispatcher = StandardTestDispatcher()
    private val testCoroutineScope = TestScope(testCoroutineDispatcher + Job())
    private val testContext: Context = ApplicationProvider.getApplicationContext()

    private val testDataStore: DataStore<Preferences> = PreferenceDataStoreFactory.create(
        scope = testCoroutineScope,
        produceFile = { testContext.preferencesDataStoreFile(TEST_DATASTORE_NAME) }
    )

    private val repository: ReminderTimeRepository = DataStoreReminderTimeRepository(testDataStore)

    @Test
    fun repository_whenDataStoreIsEmpty_returnsNull() = testCoroutineScope.runTest {
        cleanDataStore()

        val reminderTimePreferences = repository.reminderTimeFlow().first()

        Truth.assertThat(reminderTimePreferences).isEqualTo(null)
    }


    @Test
    fun repository_whenPreferenceStoredInDataStore_returnsStoredPreference() =
        testCoroutineScope.runTest {
            cleanDataStore()

            val reminderTimePreferences = ReminderTimePreferences(
                hour = 8,
                minute = 0,
            )

            repository.storeReminderTime(reminderTimePreferences)
            val storedReminderTimePreferences = repository.reminderTimeFlow().first()

            Truth.assertThat(storedReminderTimePreferences).isEqualTo(reminderTimePreferences)
        }


    @Test
    fun repository_whenPreferencesCleared_returnsNull() = testCoroutineScope.runTest {
        cleanDataStore()

        val reminderTimePreferences = ReminderTimePreferences(
            hour = 8,
            minute = 0,
        )

        repository.storeReminderTime(reminderTimePreferences)
        repository.clearReminderTime()
        val storedReminderTimePreferences = repository.reminderTimeFlow().first()

        Truth.assertThat(storedReminderTimePreferences).isEqualTo(null)
    }


    private suspend fun cleanDataStore() {
        testDataStore.edit { it.clear() }
    }
}