package it.nlp.frontend.data.remote.authentication.repository.impl

import it.nlp.frontend.data.remote.authentication.model.dto.RefreshTokenInput
import it.nlp.frontend.data.remote.authentication.model.dto.RefreshTokenOutput
import it.nlp.frontend.data.remote.authentication.model.dto.UserInput
import it.nlp.frontend.data.remote.authentication.model.dto.UserOutput
import it.nlp.frontend.data.remote.authentication.repository.AuthenticationRepository
import it.nlp.frontend.data.remote.authentication.repository.AuthenticationService
import it.nlp.frontend.data.remote.model.ApiResponse
import it.nlp.frontend.data.remote.core.ApiClient
import javax.inject.Inject

class NetworkAuthenticationRepository @Inject constructor(
    private val apiClient: ApiClient,
    private val authenticationService: AuthenticationService,
) : AuthenticationRepository {
    override suspend fun logIn(userInput: UserInput): ApiResponse<UserOutput> =
        apiClient.makeRequest { authenticationService.logIn(userInput) }

    override suspend fun signUp(userInput: UserInput): ApiResponse<UserOutput> =
        apiClient.makeRequest { authenticationService.signUp(userInput) }

    override suspend fun refreshToken(refreshTokenInput: RefreshTokenInput): ApiResponse<RefreshTokenOutput> =
        apiClient.makeRequest { authenticationService.refreshToken(refreshTokenInput) }
}
