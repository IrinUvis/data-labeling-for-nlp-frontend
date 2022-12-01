package it.winter2223.bachelor.ak.frontend.data.remote.authentication.repository.impl

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.utils.io.errors.IOException
import it.winter2223.bachelor.ak.frontend.data.core.model.DataResult
import it.winter2223.bachelor.ak.frontend.data.remote.authentication.model.dto.RefreshTokenInput
import it.winter2223.bachelor.ak.frontend.data.remote.authentication.model.dto.RefreshTokenOutput
import it.winter2223.bachelor.ak.frontend.data.remote.authentication.model.dto.UserInput
import it.winter2223.bachelor.ak.frontend.data.remote.authentication.model.dto.UserOutput
import it.winter2223.bachelor.ak.frontend.data.remote.authentication.model.exception.toAuthenticationException
import it.winter2223.bachelor.ak.frontend.data.remote.authentication.repository.AuthenticationRepository
import it.winter2223.bachelor.ak.frontend.data.remote.model.exception.ApiException
import it.winter2223.bachelor.ak.frontend.data.remote.model.exception.NetworkException
import it.winter2223.bachelor.ak.frontend.di.BASE_URL
import javax.inject.Inject

class NetworkAuthenticationRepository @Inject constructor(
    private val httpClient: HttpClient,
) : AuthenticationRepository {
    companion object {
        private const val TAG = "NetworkAuthRepo"
        private const val URL = "$BASE_URL/auth"
    }

    override suspend fun logIn(userInput: UserInput): DataResult<UserOutput, ApiException> {
        return try {
            val response = httpClient.post("$URL/user") {
                contentType(ContentType.Application.Json)
                setBody(userInput)
            }
            val userOutput = response.body<UserOutput>()
            DataResult.Success(userOutput)
        } catch (e: ResponseException) {
            Log.e(TAG, "logIn: response status is ${e.response.status}", e)
            DataResult.Failure(e.toAuthenticationException())
        } catch (e: IOException) {
            Log.e(TAG, "logIn: Network error", e)
            DataResult.Failure(NetworkException(e.message, e.cause))
        }
    }

    override suspend fun signUp(userInput: UserInput): DataResult<UserOutput, ApiException> {
        return try {
            val response = httpClient.post(URL) {
                contentType(ContentType.Application.Json)
                setBody(userInput)
            }
            val userOutput = response.body<UserOutput>()
            DataResult.Success(userOutput)
        } catch (e: ResponseException) {
            Log.e(TAG, "signUp: response status is ${e.response.status}", e)
            DataResult.Failure(e.toAuthenticationException())
        } catch (e: IOException) {
            Log.e(TAG, "signUp: Network error", e)
            DataResult.Failure(NetworkException(e.message, e.cause))
        }
    }

    override suspend fun refreshToken(refreshTokenInput: RefreshTokenInput):
            DataResult<RefreshTokenOutput, ApiException> {
        return try {
            val response = httpClient.post("$URL/token") {
                contentType(ContentType.Application.Json)
                setBody(refreshTokenInput)
            }
            val refreshTokenOutput = response.body<RefreshTokenOutput>()
            DataResult.Success(refreshTokenOutput)
        } catch (e: ResponseException) {
            Log.e(TAG, "refreshToken: response status is ${e.response.status}", e)
            DataResult.Failure(e.toAuthenticationException())
        } catch (e: IOException) {
            Log.e(TAG, "refreshToken: Network error", e)
            DataResult.Failure(NetworkException(e.message, e.cause))
        }
    }
}
