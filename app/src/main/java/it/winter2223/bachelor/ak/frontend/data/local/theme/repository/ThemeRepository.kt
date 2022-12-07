package it.winter2223.bachelor.ak.frontend.data.local.theme.repository

import it.winter2223.bachelor.ak.frontend.data.local.theme.model.ThemePreferences
import kotlinx.coroutines.flow.Flow

interface ThemeRepository {
    suspend fun storeTheme(themePreferences: ThemePreferences)

    suspend fun themeFlow(): Flow<ThemePreferences>
}
