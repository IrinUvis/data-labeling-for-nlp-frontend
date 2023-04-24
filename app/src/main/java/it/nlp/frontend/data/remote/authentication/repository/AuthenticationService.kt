package it.nlp.frontend.data.remote.authentication.repository

import it.nlp.frontend.data.remote.authentication.model.dto.RefreshTokenInput
import it.nlp.frontend.data.remote.authentication.model.dto.RefreshTokenOutput
import it.nlp.frontend.data.remote.authentication.model.dto.UserInput
import it.nlp.frontend.data.remote.authentication.model.dto.UserOutput
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationService {
    companion object {
        private const val URL = "auth"
    }

    @POST("$URL/authenticate")
    suspend fun logIn(
        @Body userInput: UserInput,
    ): Response<UserOutput>

    @POST("$URL/register")
    suspend fun signUp(
        @Body userInput: UserInput,
    ): Response<UserOutput>

    @POST("$URL/token")
    suspend fun refreshToken(
        @Body refreshTokenInput: RefreshTokenInput,
    ): Response<RefreshTokenOutput>
}
