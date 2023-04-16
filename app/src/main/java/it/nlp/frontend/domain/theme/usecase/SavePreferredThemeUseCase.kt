package it.nlp.frontend.domain.theme.usecase

import it.nlp.frontend.domain.theme.model.SavePreferredThemeResult
import it.nlp.frontend.domain.theme.model.Theme

interface SavePreferredThemeUseCase {
    suspend operator fun invoke(theme: Theme): SavePreferredThemeResult
}
