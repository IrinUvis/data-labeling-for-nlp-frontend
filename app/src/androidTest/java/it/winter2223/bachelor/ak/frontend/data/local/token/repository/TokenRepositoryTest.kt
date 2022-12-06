package it.winter2223.bachelor.ak.frontend.data.local.token.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import it.winter2223.bachelor.ak.frontend.data.local.token.model.TokenPreferences
import it.winter2223.bachelor.ak.frontend.data.local.token.repository.impl.DataStoreTokenRepository
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
class TokenRepositoryTest {
    companion object {
        private const val TEST_DATASTORE_NAME = "test_datastore_token"
    }

    private val testCoroutineDispatcher: TestDispatcher = StandardTestDispatcher()
    private val testCoroutineScope = TestScope(testCoroutineDispatcher + Job())
    private val testContext: Context = ApplicationProvider.getApplicationContext()

    private val testDataStore: DataStore<Preferences> = PreferenceDataStoreFactory.create(
        scope = testCoroutineScope,
        produceFile = { testContext.preferencesDataStoreFile(TEST_DATASTORE_NAME) }
    )

    private val repository = DataStoreTokenRepository(testDataStore)

    @Test
    fun repository_whenDataStoreIsEmpty_returnsNull() = testCoroutineScope.runTest {
        cleanDataStore()

        val tokenPreferences = repository.tokenFlow().first()

        Truth.assertThat(tokenPreferences).isNull()
    }


    @Test
    fun repository_whenPreferenceStoredInDataStore_returnsStoredPreference() = testCoroutineScope.runTest {
        cleanDataStore()

        val tokenPreferences = TokenPreferences(
            authToken = "auth_token",
            refreshToken = "refresh_token",
            userId = "user_id"
        )

        repository.storeToken(tokenPreferences)
        val storedTokenPreferences = repository.tokenFlow().first()

        Truth.assertThat(storedTokenPreferences).isEqualTo(tokenPreferences)
    }


    @Test
    fun repository_whenPreferencesCleared_returnsNull() = testCoroutineScope.runTest {
        cleanDataStore()

        val tokenPreferences = TokenPreferences(
            authToken = "auth_token",
            refreshToken = "refresh_token",
            userId = "user_id"
        )

        repository.storeToken(tokenPreferences)
        repository.clearToken()
        val storedTokenPreferences = repository.tokenFlow().first()

        Truth.assertThat(storedTokenPreferences).isNull()
    }


    private suspend fun cleanDataStore() {
        testDataStore.edit { it.clear() }
    }
}