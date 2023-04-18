package it.nlp.frontend.di

import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.RefreshTokensParams
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.utils.io.errors.IOException
import it.nlp.frontend.data.local.token.model.TokenPreferences
import it.nlp.frontend.data.local.token.repository.TokenRepository
import it.nlp.frontend.data.remote.authentication.model.dto.RefreshTokenInput
import it.nlp.frontend.data.remote.authentication.model.dto.RefreshTokenOutput
import kotlinx.coroutines.flow.first
import kotlinx.serialization.json.Json
import javax.inject.Singleton

const val BASE_URL = "http://10.0.2.2:8080/api/v1"

@InstallIn(SingletonComponent::class)
@Module
object ApiModule {
    private const val TAG = "HttpClient"
    private const val REQUEST_TIMEOUT_MILLIS = 30000L

    @Singleton
    @Provides
    fun provideApiClient(
        tokenRepository: TokenRepository,
    ): HttpClient {
        return HttpClient(Android) {
            // Features
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.d(TAG, message)
                    }
                }
                level = LogLevel.ALL
            }
            install(HttpTimeout) {
                requestTimeoutMillis = REQUEST_TIMEOUT_MILLIS
            }
            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                    }
                )
            }
            install(Auth) {
                bearer {
                    loadTokens {
                        tokenRepository.tokenFlow().first()?.let {
                            BearerTokens(
                                accessToken = it.authToken,
                                refreshToken = it.refreshToken,
                            )
                        }
                    }
                    refreshTokens {
                        refreshToken(tokenRepository)
                    }
                    // Always send the request without auth token first and only if it returns 401
                    // refreshTokens and perform request again. Very important
                    sendWithoutRequest { false }
                }
            }

            developmentMode = true // TODO: Change to false at deployment
            expectSuccess = true
        }
    }

    private suspend fun RefreshTokensParams.refreshToken(
        tokenRepository: TokenRepository,
    ): BearerTokens? {
        return oldTokens?.let { oldToken ->
            val refreshTokenInput = RefreshTokenInput(oldToken.refreshToken)
            try {
                val response = client.post("$BASE_URL/auth/token") {
                    contentType(ContentType.Application.Json)
                    setBody(refreshTokenInput)
                    markAsRefreshTokenRequest()
                }
                val refreshTokenOutput = response.body<RefreshTokenOutput>().also {
                    tokenRepository.storeToken(
                        TokenPreferences(
                            authToken = it.authToken,
                            refreshToken = it.refreshToken,
                            userId = it.userId
                        )
                    )
                }
                BearerTokens(
                    accessToken = refreshTokenOutput.authToken,
                    refreshToken = refreshTokenOutput.refreshToken,
                )
            } catch (e: ResponseException) {
                Log.e(TAG, "refreshToken callback: response status is ${e.response.status}", e)
                tokenRepository.clearToken()
                null
            } catch (e: IOException) {
                Log.e(TAG, "refreshToken callback: Network error", e)
                tokenRepository.clearToken()
                null
            }
        }
    }
}
