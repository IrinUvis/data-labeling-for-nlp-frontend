package it.nlp.frontend.data.local.theme.repository

import it.nlp.frontend.data.local.theme.model.ThemePreferences
import kotlinx.coroutines.flow.Flow

interface ThemeRepository {
    suspend fun storeTheme(themePreferences: ThemePreferences)

    suspend fun themeFlow(): Flow<ThemePreferences>
}
