package it.winter2223.bachelor.ak.frontend.domain.theme.usecase.impl

import it.winter2223.bachelor.ak.frontend.data.local.theme.model.ThemePreferences
import it.winter2223.bachelor.ak.frontend.data.local.theme.repository.ThemeRepository
import it.winter2223.bachelor.ak.frontend.domain.theme.model.SavePreferredThemeResult
import it.winter2223.bachelor.ak.frontend.domain.theme.model.Theme
import it.winter2223.bachelor.ak.frontend.domain.theme.usecase.SavePreferredThemeUseCase
import java.io.IOException
import javax.inject.Inject

class ProdSavePreferredThemeUseCase @Inject constructor(
    private val themeRepository: ThemeRepository,
) : SavePreferredThemeUseCase {
    override suspend fun invoke(theme: Theme): SavePreferredThemeResult {
        return try {
            themeRepository.storeTheme(
                ThemePreferences.valueOf(theme.name)
            )
            SavePreferredThemeResult.Success
        } catch (e: IOException) {
            SavePreferredThemeResult.Failure(e)
        }
    }
}
