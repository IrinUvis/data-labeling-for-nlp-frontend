package it.winter2223.bachelor.ak.frontend.data.remote.authentication.repository.impl

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import it.winter2223.bachelor.ak.frontend.data.core.model.DataResult
import it.winter2223.bachelor.ak.frontend.data.remote.authentication.model.dto.RefreshTokenInput
import it.winter2223.bachelor.ak.frontend.data.remote.authentication.model.dto.RefreshTokenOutput
import it.winter2223.bachelor.ak.frontend.data.remote.authentication.model.dto.UserInput
import it.winter2223.bachelor.ak.frontend.data.remote.authentication.model.dto.UserOutput
import it.winter2223.bachelor.ak.frontend.data.remote.authentication.model.exception.AuthenticationException
import it.winter2223.bachelor.ak.frontend.data.remote.authentication.model.exception.toAuthenticationException
import it.winter2223.bachelor.ak.frontend.data.remote.authentication.repository.AuthenticationRepository
import it.winter2223.bachelor.ak.frontend.di.BASE_URL
import javax.inject.Inject

class NetworkAuthenticationRepository @Inject constructor(
    private val httpClient: HttpClient,
) : AuthenticationRepository {
    companion object {
        private const val TAG = "DemoLogInRepo"
        private const val URL = "$BASE_URL/auth"
    }

    override suspend fun logIn(userInput: UserInput): DataResult<UserOutput, AuthenticationException> {
        return try {
            val response = httpClient.post("$URL/user") {
                setBody(userInput)
            }
            val userOutput = response.body<UserOutput>()
            DataResult.Success(userOutput)
        } catch (e: RedirectResponseException) {
            Log.e(TAG, "logIn: response status is ${e.response.status}", e)
            DataResult.Failure(e.toAuthenticationException())
        } catch (e: ClientRequestException) {
            Log.e(TAG, "logIn: response status is ${e.response.status}", e)
            DataResult.Failure(e.toAuthenticationException())
        } catch (e: ServerResponseException) {
            Log.e(TAG, "logIn: response status is ${e.response.status}", e)
            DataResult.Failure(e.toAuthenticationException())
        }
    }

    override suspend fun signUp(userInput: UserInput): DataResult<UserOutput, AuthenticationException> {
        return try {
            val response = httpClient.post(URL) {
                setBody(userInput)
            }
            val userOutput = response.body<UserOutput>()
            DataResult.Success(userOutput)
        } catch (e: RedirectResponseException) {
            Log.e(TAG, "signUp: response status is ${e.response.status}", e)
            DataResult.Failure(e.toAuthenticationException())
        } catch (e: ClientRequestException) {
            Log.e(TAG, "signUp: response status is ${e.response.status}", e)
            DataResult.Failure(e.toAuthenticationException())
        } catch (e: ServerResponseException) {
            Log.e(TAG, "signUp: response status is ${e.response.status}", e)
            DataResult.Failure(e.toAuthenticationException())
        }
    }

    override suspend fun refreshToken(refreshTokenInput: RefreshTokenInput):
            DataResult<RefreshTokenOutput, AuthenticationException> {
        return try {
            val response = httpClient.post("$URL/token") {
                setBody(refreshTokenInput)
            }
            val refreshTokenOutput = response.body<RefreshTokenOutput>()
            DataResult.Success(refreshTokenOutput)
        } catch (e: RedirectResponseException) {
            Log.e(TAG, "signUp: response status is ${e.response.status}", e)
            DataResult.Failure(e.toAuthenticationException())
        } catch (e: ClientRequestException) {
            Log.e(TAG, "signUp: response status is ${e.response.status}", e)
            DataResult.Failure(e.toAuthenticationException())
        } catch (e: ServerResponseException) {
            Log.e(TAG, "signUp: response status is ${e.response.status}", e)
            DataResult.Failure(e.toAuthenticationException())
        }
    }
}