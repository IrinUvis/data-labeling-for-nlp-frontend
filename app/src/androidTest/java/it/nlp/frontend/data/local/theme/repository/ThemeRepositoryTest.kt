package it.nlp.frontend.data.local.theme.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import it.winter2223.bachelor.ak.frontend.data.local.theme.model.ThemePreferences
import it.winter2223.bachelor.ak.frontend.data.local.theme.repository.ThemeRepository
import it.winter2223.bachelor.ak.frontend.data.local.theme.repository.impl.DataStoreThemeRepository
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
class ThemeRepositoryTest {
    companion object {
        private const val TEST_DATASTORE_NAME = "test_datastore_theme"
    }

    private val testCoroutineDispatcher: TestDispatcher = StandardTestDispatcher()
    private val testCoroutineScope = TestScope(testCoroutineDispatcher + Job())
    private val testContext: Context = ApplicationProvider.getApplicationContext()

    private val testDataStore: DataStore<Preferences> = PreferenceDataStoreFactory.create(
        scope = testCoroutineScope,
        produceFile = { testContext.preferencesDataStoreFile(TEST_DATASTORE_NAME) }
    )

    private val repository: ThemeRepository = DataStoreThemeRepository(testDataStore)

    @Test
    fun repository_whenDataStoreIsEmpty_returnsSystemDefaultTheme() = testCoroutineScope.runTest {
        cleanDataStore()

        val themePreferences = repository.themeFlow().first()

        Truth.assertThat(themePreferences).isEqualTo(ThemePreferences.System)
    }


    @Test
    fun repository_whenPreferenceStoredInDataStore_returnsStoredPreference() =
        testCoroutineScope.runTest {
            cleanDataStore()

            val themePreferences = ThemePreferences.Dark

            repository.storeTheme(themePreferences)
            val storedThemePreferences = repository.themeFlow().first()

            Truth.assertThat(storedThemePreferences).isEqualTo(themePreferences)
        }


    @Test
    fun repository_whenStoredPreferenceOverwritten_returnsNewlyStoredPreference() =
        testCoroutineScope.runTest {
            cleanDataStore()

            val themePreferences = ThemePreferences.Dark
            val overwrittenThemePreferences = ThemePreferences.Light

            repository.storeTheme(themePreferences)
            repository.storeTheme(overwrittenThemePreferences)

            val storedThemePreferences = repository.themeFlow().first()

            Truth.assertThat(storedThemePreferences).isEqualTo(overwrittenThemePreferences)
        }


    private suspend fun cleanDataStore() {
        testDataStore.edit { it.clear() }
    }
}