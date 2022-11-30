package it.winter2223.bachelor.ak.frontend.data.remote.authentication.repository.impl

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import it.winter2223.bachelor.ak.frontend.data.remote.authentication.model.dto.RefreshTokenInput
import it.winter2223.bachelor.ak.frontend.data.remote.authentication.model.dto.RefreshTokenOutput
import it.winter2223.bachelor.ak.frontend.data.remote.authentication.model.dto.UserInput
import it.winter2223.bachelor.ak.frontend.data.remote.authentication.model.dto.UserOutput
import it.winter2223.bachelor.ak.frontend.data.remote.authentication.repository.LogInRepository
import it.winter2223.bachelor.ak.frontend.di.BASE_URL
import javax.inject.Inject

class NetworkLogInRepository @Inject constructor(
    private val httpClient: HttpClient,
) : LogInRepository {
    companion object {
        private const val TAG = "DemoLogInRepo"
        private const val URL = "$BASE_URL/auth"
    }

    override suspend fun logIn(userInput: UserInput): Result<UserOutput> {
        return try {
            val response = httpClient.post("$URL/user") {
                setBody(userInput)
            }
            val userOutput = response.body<UserOutput>()
            Result.success(userOutput)
        } catch (e: RedirectResponseException) {
            Log.e(TAG, "logIn: response status is ${e.response.status}", e)
            Result.failure(e)
        } catch (e: ClientRequestException) {
            Log.e(TAG, "logIn: response status is ${e.response.status}", e)
            Result.failure(e)
        } catch (e: ServerResponseException) {
            Log.e(TAG, "logIn: response status is ${e.response.status}", e)
            Result.failure(e)
        }
    }

    override suspend fun signUp(userInput: UserInput): Result<UserOutput> {
        return try {
            val response = httpClient.post(URL) {
                setBody(userInput)
            }
            val userOutput = response.body<UserOutput>()
            Result.success(userOutput)
        } catch (e: RedirectResponseException) {
            Log.e(TAG, "signUp: response status is ${e.response.status}", e)
            Result.failure(e)
        } catch (e: ClientRequestException) {
            Log.e(TAG, "signUp: response status is ${e.response.status}", e)
            Result.failure(e)
        } catch (e: ServerResponseException) {
            Log.e(TAG, "signUp: response status is ${e.response.status}", e)
            Result.failure(e)
        }
    }

    override suspend fun refreshToken(refreshTokenInput: RefreshTokenInput): Result<RefreshTokenOutput> {
        return try {
            val response = httpClient.post("$URL/token") {
                setBody(refreshTokenInput)
            }
            val userOutput = response.body<RefreshTokenOutput>()
            Result.success(userOutput)
        } catch (e: RedirectResponseException) {
            Log.e(TAG, "refreshToken: response status is ${e.response.status}", e)
            Result.failure(e)
        } catch (e: ClientRequestException) {
            Log.e(TAG, "refreshToken: response status is ${e.response.status}", e)
            Result.failure(e)
        } catch (e: ServerResponseException) {
            Log.e(TAG, "refreshToken: response status is ${e.response.status}", e)
            Result.failure(e)
        }
    }
}
