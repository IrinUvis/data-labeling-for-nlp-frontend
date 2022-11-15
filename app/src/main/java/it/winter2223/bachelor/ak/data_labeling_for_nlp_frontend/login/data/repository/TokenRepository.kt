package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.data.repository

import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.data.model.Token
import kotlinx.coroutines.flow.Flow

interface TokenRepository {

    suspend fun storeToken(token: Token)

    suspend fun clearToken()

    suspend fun tokenFlow(): Flow<Token?>

    suspend fun getToken(): Token?
}
