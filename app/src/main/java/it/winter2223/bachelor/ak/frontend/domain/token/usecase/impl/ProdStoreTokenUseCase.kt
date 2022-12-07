package it.winter2223.bachelor.ak.frontend.domain.token.usecase.impl

import it.winter2223.bachelor.ak.frontend.data.local.token.model.TokenPreferences
import it.winter2223.bachelor.ak.frontend.data.local.token.repository.TokenRepository
import it.winter2223.bachelor.ak.frontend.domain.token.model.StoreTokenResult
import it.winter2223.bachelor.ak.frontend.domain.token.model.Token
import it.winter2223.bachelor.ak.frontend.domain.token.usecase.StoreTokenUseCase
import java.io.IOException
import javax.inject.Inject

class ProdStoreTokenUseCase @Inject constructor(
    private val tokenRepository: TokenRepository,
) : StoreTokenUseCase {
    override suspend fun invoke(token: Token): StoreTokenResult {
        return try {
            tokenRepository.storeToken(
                TokenPreferences(
                    authToken = token.authToken,
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
