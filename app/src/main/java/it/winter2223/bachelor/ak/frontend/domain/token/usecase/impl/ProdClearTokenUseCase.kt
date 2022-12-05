package it.winter2223.bachelor.ak.frontend.domain.token.usecase.impl

import android.util.Log
import it.winter2223.bachelor.ak.frontend.data.local.token.repository.TokenRepository
import it.winter2223.bachelor.ak.frontend.domain.token.model.ClearTokenResult
import it.winter2223.bachelor.ak.frontend.domain.token.usecase.ClearTokenUseCase
import java.io.IOException
import javax.inject.Inject

class ProdClearTokenUseCase @Inject constructor(
    private val tokenRepository: TokenRepository
) : ClearTokenUseCase {
    companion object {
        private const val TAG = "ProdClearTokenUC"
    }
    override suspend fun invoke(): ClearTokenResult {
        return try {
            tokenRepository.clearToken()
            ClearTokenResult.Success
        } catch (e: IOException) {
            Log.e(TAG, "Token has not been cleared successfully", e)
            ClearTokenResult.Failure
        }
    }
}
