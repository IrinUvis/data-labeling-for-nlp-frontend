package it.nlp.frontend.data.remote.authentication.repository

import it.nlp.frontend.data.remote.authentication.model.dto.RefreshTokenInput
import it.nlp.frontend.data.remote.authentication.model.dto.RefreshTokenOutput
import it.nlp.frontend.data.remote.authentication.model.dto.UserInput
import it.nlp.frontend.data.remote.authentication.model.dto.UserOutput
import it.nlp.frontend.data.remote.model.ApiResponse

interface AuthenticationRepository {
    suspend fun logIn(userInput: UserInput): ApiResponse<UserOutput>

    suspend fun signUp(userInput: UserInput): ApiResponse<UserOutput>

    suspend fun refreshToken(refreshTokenInput: RefreshTokenInput): ApiResponse<RefreshTokenOutput>
}
