package it.winter2223.bachelor.ak.frontend.domain.token.usecase.impl

import it.winter2223.bachelor.ak.frontend.data.remote.authentication.model.dto.RefreshTokenInput
import it.winter2223.bachelor.ak.frontend.data.remote.authentication.repository.LogInRepository
import it.winter2223.bachelor.ak.frontend.domain.token.model.RefreshTokenResult
import it.winter2223.bachelor.ak.frontend.domain.token.model.StoreTokenResult
import it.winter2223.bachelor.ak.frontend.domain.token.model.Token
import it.winter2223.bachelor.ak.frontend.domain.token.usecase.RefreshTokenUseCase
import it.winter2223.bachelor.ak.frontend.domain.token.usecase.StoreTokenUseCase

class ProdRefreshTokenUseCase(
    private val logInRepository: LogInRepository,
    private val storeTokenUseCase: StoreTokenUseCase,
) : RefreshTokenUseCase {
    override suspend fun invoke(refreshToken: String): RefreshTokenResult {
        val refreshedToken = logInRepository.refreshToken(RefreshTokenInput(refreshToken))

        return refreshedToken.fold(
            onSuccess = { tokenOutput ->
                val storeTokenResult = storeTokenUseCase(
                    Token(
                        authToken = tokenOutput.authToken,
                        refreshToken = tokenOutput.refreshToken,
                        userId = tokenOutput.userId,
                    ),
                )
                when (storeTokenResult) {
                    is StoreTokenResult.Success -> RefreshTokenResult.Success
                    else -> RefreshTokenResult.Failure.DataStore
                }
            },
            onFailure = {
                RefreshTokenResult.Failure.Network
            }
        )
    }
}
