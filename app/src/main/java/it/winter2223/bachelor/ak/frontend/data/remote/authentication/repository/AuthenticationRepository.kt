package it.winter2223.bachelor.ak.frontend.data.remote.authentication.repository

import it.winter2223.bachelor.ak.frontend.data.core.model.DataResult
import it.winter2223.bachelor.ak.frontend.data.remote.authentication.model.dto.UserInput
import it.winter2223.bachelor.ak.frontend.data.remote.authentication.model.dto.UserOutput
import it.winter2223.bachelor.ak.frontend.data.remote.model.exception.ApiException

interface AuthenticationRepository {
    suspend fun logIn(userInput: UserInput): DataResult<UserOutput, ApiException>

    suspend fun signUp(userInput: UserInput): DataResult<UserOutput, ApiException>
}
