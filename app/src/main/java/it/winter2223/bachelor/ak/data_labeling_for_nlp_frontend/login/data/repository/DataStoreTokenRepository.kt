package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.data.model.Token
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreTokenRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : TokenRepository {
    companion object {
        private object TokenPreferencesKeys {
            val AUTH_TOKEN = stringPreferencesKey("auth_token")
            val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
        }
    }

    override suspend fun storeToken(token: Token) {
        dataStore.edit { preferences ->
            preferences[TokenPreferencesKeys.AUTH_TOKEN] = token.authToken
            preferences[TokenPreferencesKeys.REFRESH_TOKEN] = token.refreshToken
        }
    }

    override suspend fun clearToken() {
        dataStore.edit { preferences ->
            preferences.remove(TokenPreferencesKeys.AUTH_TOKEN)
            preferences.remove(TokenPreferencesKeys.REFRESH_TOKEN)
        }
    }

    override suspend fun tokenFlow(): Flow<Token?> {
        return dataStore.data.map { preferences ->
            val authToken = preferences[TokenPreferencesKeys.AUTH_TOKEN]
            val refreshToken = preferences[TokenPreferencesKeys.REFRESH_TOKEN]

            if (authToken == null || refreshToken == null) {
                null
            } else {
                Token(
                    authToken = authToken,
                    refreshToken = refreshToken,
                )
            }
        }
    }

    override suspend fun getToken(): Token? {
        return dataStore.data.map { preferences ->
            val authToken = preferences[TokenPreferencesKeys.AUTH_TOKEN]
            val refreshToken = preferences[TokenPreferencesKeys.REFRESH_TOKEN]

            if (authToken == null || refreshToken == null) {
                null
            } else {
                Token(
                    authToken = authToken,
                    refreshToken = refreshToken,
                )
            }
        }.first()
    }
}

