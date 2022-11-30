package it.winter2223.bachelor.ak.frontend.data.remote.authentication.repository

import it.winter2223.bachelor.ak.frontend.data.core.model.DataResult
import it.winter2223.bachelor.ak.frontend.data.remote.authentication.model.dto.RefreshTokenInput
import it.winter2223.bachelor.ak.frontend.data.remote.authentication.model.dto.RefreshTokenOutput
import it.winter2223.bachelor.ak.frontend.data.remote.authentication.model.dto.UserInput
import it.winter2223.bachelor.ak.frontend.data.remote.authentication.model.dto.UserOutput
import it.winter2223.bachelor.ak.frontend.data.remote.authentication.model.exception.AuthenticationException

interface AuthenticationRepository {
    suspend fun logIn(userInput: UserInput): DataResult<UserOutput, AuthenticationException>

    suspend fun signUp(userInput: UserInput): DataResult<UserOutput, AuthenticationException>

    suspend fun refreshToken(refreshTokenInput: RefreshTokenInput):
            DataResult<RefreshTokenOutput, AuthenticationException>
}
