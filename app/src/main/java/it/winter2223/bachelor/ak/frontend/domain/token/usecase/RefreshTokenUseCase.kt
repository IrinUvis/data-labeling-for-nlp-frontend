package it.winter2223.bachelor.ak.frontend.domain.token.usecase

import it.winter2223.bachelor.ak.frontend.domain.token.model.RefreshTokenResult

interface RefreshTokenUseCase {
    suspend operator fun invoke(refreshToken: String): RefreshTokenResult
}
