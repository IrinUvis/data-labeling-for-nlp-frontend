package it.winter2223.bachelor.ak.frontend.domain.token.usecase

import it.winter2223.bachelor.ak.frontend.domain.token.model.GetTokenFlowResult

interface GetTokenFlowUseCase {
    suspend operator fun invoke(): GetTokenFlowResult
}
