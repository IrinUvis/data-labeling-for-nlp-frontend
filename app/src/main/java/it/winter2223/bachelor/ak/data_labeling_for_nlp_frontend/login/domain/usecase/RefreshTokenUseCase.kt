package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.domain.usecase

import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.domain.model.RefreshTokenResult

interface RefreshTokenUseCase {
    suspend operator fun invoke(refreshToken: String): RefreshTokenResult
}
