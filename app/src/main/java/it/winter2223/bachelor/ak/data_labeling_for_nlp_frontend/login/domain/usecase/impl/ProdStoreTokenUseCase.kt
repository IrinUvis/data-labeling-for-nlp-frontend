package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.domain.usecase.impl

import android.util.Log
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.data.model.TokenPreferences
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.data.repository.TokenRepository
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.domain.model.StoreTokenResult
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.domain.model.Token
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.domain.usecase.StoreTokenUseCase
import java.io.IOException
import javax.inject.Inject

class ProdStoreTokenUseCase @Inject constructor(
    private val tokenRepository: TokenRepository,
) : StoreTokenUseCase {
    companion object {
        private const val TAG = "ProdStoreTokenUC"
    }
    override suspend fun invoke(token: Token): StoreTokenResult {
        return try {
            tokenRepository.storeToken(
                TokenPreferences(
                    authToken = token.authToken,
                    refreshToken = token.refreshToken,
                )
            )
            StoreTokenResult.Success
        } catch (e: IOException) {
            Log.e(TAG, "An error has occurred during an attempt to store token $token", e)
            StoreTokenResult.Failure
        }
    }
}
