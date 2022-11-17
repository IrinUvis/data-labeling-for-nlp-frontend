package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.domain.usecase

import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.domain.model.StoreTokenResult
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.domain.model.Token

interface StoreTokenUseCase {
    suspend operator fun invoke(token: Token): StoreTokenResult
}
