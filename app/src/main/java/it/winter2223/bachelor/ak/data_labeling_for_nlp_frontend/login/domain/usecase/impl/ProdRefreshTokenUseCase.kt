package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.domain.usecase.impl

import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.data.model.dto.RefreshTokenInput
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.data.repository.LogInRepository
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.domain.model.RefreshTokenResult
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.domain.model.StoreTokenResult
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.domain.model.Token
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.domain.usecase.RefreshTokenUseCase
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.domain.usecase.StoreTokenUseCase

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
                        authToken = tokenOutput.idToken,
                        refreshToken = tokenOutput.refreshToken,
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
