package it.nlp.frontend.data.local.token.repository

import it.nlp.frontend.data.local.token.model.TokenPreferences
import kotlinx.coroutines.flow.Flow

interface TokenRepository {
    suspend fun storeToken(tokenPreferences: TokenPreferences)

    suspend fun clearToken()

    suspend fun tokenFlow(): Flow<TokenPreferences?>
}
