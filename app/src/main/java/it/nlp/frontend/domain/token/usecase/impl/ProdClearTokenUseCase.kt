package it.nlp.frontend.domain.token.usecase.impl

import it.nlp.frontend.data.local.token.repository.TokenRepository
import it.nlp.frontend.domain.token.model.ClearTokenResult
import it.nlp.frontend.domain.token.usecase.ClearTokenUseCase
import java.io.IOException
import javax.inject.Inject

class ProdClearTokenUseCase @Inject constructor(
    private val tokenRepository: TokenRepository
) : ClearTokenUseCase {
    override suspend fun invoke(): ClearTokenResult {
        return try {
            tokenRepository.clearToken()
            ClearTokenResult.Success
        } catch (e: IOException) {
            ClearTokenResult.Failure(e)
        }
    }
}
