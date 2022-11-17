package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.data.repository

import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.data.model.TokenPreferences
import kotlinx.coroutines.flow.Flow

interface TokenRepository {
    suspend fun storeToken(tokenPreferences: TokenPreferences)

    suspend fun clearToken()

    suspend fun tokenFlow(): Flow<TokenPreferences?>
}
