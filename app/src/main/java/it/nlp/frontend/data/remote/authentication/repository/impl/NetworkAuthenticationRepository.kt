package it.nlp.frontend.data.remote.authentication.repository.impl

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.utils.io.errors.IOException
import it.nlp.frontend.data.core.model.DataResult
import it.nlp.frontend.data.remote.authentication.model.dto.UserInput
import it.nlp.frontend.data.remote.authentication.model.dto.UserOutput
import it.nlp.frontend.data.remote.authentication.model.exception.toAuthenticationException
import it.nlp.frontend.data.remote.authentication.repository.AuthenticationRepository
import it.nlp.frontend.data.remote.model.exception.ApiException
import it.nlp.frontend.data.remote.model.exception.NetworkException
import it.nlp.frontend.data.remote.model.exception.ServiceUnavailableException
import it.nlp.frontend.di.BASE_URL
import javax.inject.Inject

class NetworkAuthenticationRepository @Inject constructor(
    private val httpClient: HttpClient,
) : AuthenticationRepository {
    companion object {
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
            when (e.response.status) {
                HttpStatusCode.ServiceUnavailable,
                HttpStatusCode.GatewayTimeout,
                -> DataResult.Failure(
                    ServiceUnavailableException(
                        e.message,
                        e.cause,
                    )
                )
                else -> DataResult.Failure(e.toAuthenticationException())
            }
        } catch (e: IOException) {
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
            when (e.response.status) {
                HttpStatusCode.ServiceUnavailable,
                HttpStatusCode.GatewayTimeout,
                -> DataResult.Failure(
                    ServiceUnavailableException(
                        e.message,
                        e.cause,
                    )
                )
                else -> DataResult.Failure(e.toAuthenticationException())
            }
        } catch (e: IOException) {
            DataResult.Failure(NetworkException(e.message, e.cause))
        }
    }
}
