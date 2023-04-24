package it.nlp.frontend.data.remote.core

import it.nlp.frontend.data.local.token.repository.TokenRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val tokenRepository: TokenRepository
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking {
            tokenRepository.tokenFlow().first()
        }
        val request = chain.request().newBuilder()
        if (token != null) {
            request.header("Authorization", "Bearer ${token.accessToken}")
        }
        return chain.proceed(request.build())
    }
}
