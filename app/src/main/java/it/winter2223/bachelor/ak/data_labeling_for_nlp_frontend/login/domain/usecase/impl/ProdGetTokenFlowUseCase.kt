package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.domain.usecase.impl

import android.util.Log
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.data.repository.TokenRepository
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.domain.model.GetTokenFlowResult
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.domain.model.Token
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.domain.usecase.GetTokenFlowUseCase
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class ProdGetTokenFlowUseCase @Inject constructor(
    private val tokenRepository: TokenRepository,
) : GetTokenFlowUseCase {
    companion object {
        private const val TAG = "ProdGetTokenFlowUC"
    }
    override suspend fun invoke(): GetTokenFlowResult {
        return try {
            GetTokenFlowResult.Success(
                tokenFlow = tokenRepository.tokenFlow().map {
                    it?.let {
                        Token(
                            authToken = it.authToken,
                            refreshToken = it.refreshToken
                        )
                    }
                },
            )
        } catch (e: IOException) {
            Log.e(TAG, "An error has occurred while retrieving token from DataStore", e)
            GetTokenFlowResult.Failure
        }
    }
}
