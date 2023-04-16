package it.nlp.frontend.domain.theme.usecase

import it.nlp.frontend.domain.theme.model.GetThemeFlowResult

interface GetThemeFlowUseCase {
    suspend operator fun invoke(): GetThemeFlowResult
}
