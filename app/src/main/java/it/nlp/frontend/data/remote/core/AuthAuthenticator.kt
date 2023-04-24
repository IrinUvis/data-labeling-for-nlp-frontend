package it.nlp.frontend.data.remote.core

import it.nlp.frontend.data.local.token.model.TokenPreferences
import it.nlp.frontend.data.local.token.repository.TokenRepository
import it.nlp.frontend.data.remote.authentication.model.dto.RefreshTokenInput
import it.nlp.frontend.data.remote.authentication.model.dto.RefreshTokenOutput
import it.nlp.frontend.data.remote.authentication.repository.AuthenticationService
import it.nlp.frontend.di.ApiModule
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class AuthAuthenticator @Inject constructor(
    private val tokenRepository: TokenRepository,
) : Authenticator {
    /**
     * Called only when the server returned 401 (access token has expired)
     */
    override fun authenticate(route: Route?, response: Response): Request? {
        val token = runBlocking {
            tokenRepository.tokenFlow().first()
        }
        return runBlocking {
            val newTokenResponse = requestNewToken(token?.refreshToken)

            if (!newTokenResponse.isSuccessful || newTokenResponse.body() == null) {
                tokenRepository.clearToken()
            }

            newTokenResponse.body()?.let {
                tokenRepository.storeToken(
                    TokenPreferences(
                        accessToken = it.accessTokenOutput.value,
                        refreshToken = it.refreshTokenOutput.value,
                        userId = it.userId,
                    )
                )
                val request = response.request.newBuilder()
                    .header("Authorization", "Bearer ${it.accessTokenOutput.value}")
                    .build()
                request
            }
        }
    }

    private suspend fun requestNewToken(refreshToken: String?): retrofit2.Response<RefreshTokenOutput> {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

        val retrofit = Retrofit.Builder()
            .baseUrl(ApiModule.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        val authenticationService = retrofit.create(AuthenticationService::class.java)
        return authenticationService.refreshToken(RefreshTokenInput(refreshToken ?: ""))
    }
}
