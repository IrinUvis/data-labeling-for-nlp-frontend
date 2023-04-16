package it.nlp.frontend.data.remote.authentication.repository

import it.nlp.frontend.data.core.model.DataResult
import it.nlp.frontend.data.remote.authentication.model.dto.UserInput
import it.nlp.frontend.data.remote.authentication.model.dto.UserOutput
import it.nlp.frontend.data.remote.model.exception.ApiException

interface AuthenticationRepository {
    suspend fun logIn(userInput: UserInput): DataResult<UserOutput, ApiException>

    suspend fun signUp(userInput: UserInput): DataResult<UserOutput, ApiException>
}
