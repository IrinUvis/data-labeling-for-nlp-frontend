package it.nlp.frontend.data.local.theme.repository.impl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import it.nlp.frontend.data.local.theme.model.ThemePreferences
import it.nlp.frontend.data.local.theme.repository.ThemeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreThemeRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : ThemeRepository {
    companion object {
        private object ThemePreferencesKeys {
            val THEME = stringPreferencesKey("theme")
        }
    }

    override suspend fun storeTheme(themePreferences: ThemePreferences) {
        dataStore.edit { preferences ->
            preferences[ThemePreferencesKeys.THEME] = themePreferences.name
        }
    }

    override suspend fun themeFlow(): Flow<ThemePreferences> {
        return dataStore.data.map { preferences ->
            val theme = preferences[ThemePreferencesKeys.THEME]

            if (theme == null) {
                ThemePreferences.System
            } else {
                ThemePreferences.valueOf(theme)
            }
        }
    }
}
