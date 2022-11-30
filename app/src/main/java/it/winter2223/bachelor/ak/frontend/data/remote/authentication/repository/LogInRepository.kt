package it.winter2223.bachelor.ak.frontend.data.remote.authentication.repository

import it.winter2223.bachelor.ak.frontend.data.remote.authentication.model.dto.RefreshTokenInput
import it.winter2223.bachelor.ak.frontend.data.remote.authentication.model.dto.RefreshTokenOutput
import it.winter2223.bachelor.ak.frontend.data.remote.authentication.model.dto.UserInput
import it.winter2223.bachelor.ak.frontend.data.remote.authentication.model.dto.UserOutput

interface LogInRepository {
    suspend fun logIn(userInput: UserInput): Result<UserOutput>

    suspend fun signUp(userInput: UserInput): Result<UserOutput>

    suspend fun refreshToken(refreshTokenInput: RefreshTokenInput): Result<RefreshTokenOutput>
}
