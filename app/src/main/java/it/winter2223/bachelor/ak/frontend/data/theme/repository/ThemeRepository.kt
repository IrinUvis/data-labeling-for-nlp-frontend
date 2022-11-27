package it.winter2223.bachelor.ak.frontend.data.theme.repository

import it.winter2223.bachelor.ak.frontend.data.theme.model.ThemePreferences
import kotlinx.coroutines.flow.Flow

interface ThemeRepository {
    suspend fun storeTheme(themePreferences: ThemePreferences)

    suspend fun tokenFlow(): Flow<ThemePreferences>
}
