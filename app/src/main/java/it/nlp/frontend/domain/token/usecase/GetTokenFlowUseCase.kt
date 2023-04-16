package it.nlp.frontend.domain.token.usecase

import it.nlp.frontend.domain.token.model.GetTokenFlowResult

interface GetTokenFlowUseCase {
    suspend operator fun invoke(): GetTokenFlowResult
}
