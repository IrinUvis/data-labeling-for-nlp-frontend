package it.nlp.frontend.domain.token.usecase.impl

import it.nlp.frontend.data.local.token.model.TokenPreferences
import it.nlp.frontend.data.local.token.repository.TokenRepository
import it.nlp.frontend.domain.token.model.StoreTokenResult
import it.nlp.frontend.domain.token.model.Token
import it.nlp.frontend.domain.token.usecase.StoreTokenUseCase
import java.io.IOException
import javax.inject.Inject

class ProdStoreTokenUseCase @Inject constructor(
    private val tokenRepository: TokenRepository,
) : StoreTokenUseCase {
    override suspend fun invoke(token: Token): StoreTokenResult {
        return try {
            tokenRepository.storeToken(
                TokenPreferences(
                    accessToken = token.accessToken,
                    refreshToken = token.refreshToken,
                    userId = token.userId,
                )
            )
            StoreTokenResult.Success
        } catch (e: IOException) {
            StoreTokenResult.Failure(e)
        }
    }
}
