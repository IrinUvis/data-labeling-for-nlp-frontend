package it.nlp.frontend.domain.theme.usecase.impl

import it.nlp.frontend.data.local.theme.model.ThemePreferences
import it.nlp.frontend.data.local.theme.repository.ThemeRepository
import it.nlp.frontend.domain.theme.model.SavePreferredThemeResult
import it.nlp.frontend.domain.theme.model.Theme
import it.nlp.frontend.domain.theme.usecase.SavePreferredThemeUseCase
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
