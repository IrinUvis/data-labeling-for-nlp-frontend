package it.winter2223.bachelor.ak.frontend.domain.token.usecase.impl

import it.winter2223.bachelor.ak.frontend.data.local.token.repository.TokenRepository
import it.winter2223.bachelor.ak.frontend.domain.token.model.GetTokenFlowResult
import it.winter2223.bachelor.ak.frontend.domain.token.model.Token
import it.winter2223.bachelor.ak.frontend.domain.token.usecase.GetTokenFlowUseCase
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class ProdGetTokenFlowUseCase @Inject constructor(
    private val tokenRepository: TokenRepository,
) : GetTokenFlowUseCase {
    override suspend fun invoke(): GetTokenFlowResult {
        return try {
            GetTokenFlowResult.Success(
                tokenFlow = tokenRepository.tokenFlow().map {
                    it?.let {
                        Token(
                            authToken = it.authToken,
                            refreshToken = it.refreshToken,
                            userId = it.userId,
                        )
                    }
                },
            )
        } catch (e: IOException) {
            GetTokenFlowResult.Failure(e)
        }
    }
}
