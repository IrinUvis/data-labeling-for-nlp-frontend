package it.winter2223.bachelor.ak.frontend.domain.theme.usecase

import it.winter2223.bachelor.ak.frontend.domain.theme.model.GetThemeFlowResult

interface GetThemeFlowUseCase {
    suspend operator fun invoke(): GetThemeFlowResult
}
