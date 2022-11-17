package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.domain.usecase

import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.domain.model.GetTokenFlowResult

interface GetTokenFlowUseCase {
    suspend operator fun invoke(): GetTokenFlowResult
}
