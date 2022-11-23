package it.winter2223.bachelor.ak.frontend.domain.theme.usecase

import it.winter2223.bachelor.ak.frontend.domain.theme.model.SavePreferredThemeResult
import it.winter2223.bachelor.ak.frontend.domain.theme.model.Theme

interface SavePreferredThemeUseCase {
    suspend operator fun invoke(theme: Theme): SavePreferredThemeResult
}
