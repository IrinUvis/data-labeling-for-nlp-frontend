package it.nlp.frontend.domain.token.usecase

import it.nlp.frontend.domain.token.model.StoreTokenResult
import it.nlp.frontend.domain.token.model.Token

interface StoreTokenUseCase {
    suspend operator fun invoke(token: Token): StoreTokenResult
}
