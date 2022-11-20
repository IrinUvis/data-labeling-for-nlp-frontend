package it.winter2223.bachelor.ak.frontend.domain.token.usecase

import it.winter2223.bachelor.ak.frontend.domain.token.model.StoreTokenResult
import it.winter2223.bachelor.ak.frontend.domain.token.model.Token

interface StoreTokenUseCase {
    suspend operator fun invoke(token: Token): StoreTokenResult
}
