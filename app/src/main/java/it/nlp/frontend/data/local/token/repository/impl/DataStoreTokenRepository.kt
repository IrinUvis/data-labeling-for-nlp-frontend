package it.nlp.frontend.data.local.token.repository.impl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import it.nlp.frontend.data.local.token.model.TokenPreferences
import it.nlp.frontend.data.local.token.repository.TokenRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreTokenRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : TokenRepository {
    companion object {
        private object TokenPreferencesKeys {
            val ACCESS_TOKEN = stringPreferencesKey("access_token")
            val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
            val USER_ID = stringPreferencesKey("user_id")
        }
    }

    override suspend fun storeToken(tokenPreferences: TokenPreferences) {
        dataStore.edit { preferences ->
            preferences[TokenPreferencesKeys.ACCESS_TOKEN] = tokenPreferences.accessToken
            preferences[TokenPreferencesKeys.REFRESH_TOKEN] = tokenPreferences.refreshToken
            preferences[TokenPreferencesKeys.USER_ID] = tokenPreferences.userId
        }
    }

    override suspend fun clearToken() {
        dataStore.edit { preferences ->
            preferences.remove(TokenPreferencesKeys.ACCESS_TOKEN)
            preferences.remove(TokenPreferencesKeys.REFRESH_TOKEN)
            preferences.remove(TokenPreferencesKeys.USER_ID)
        }
    }

    override suspend fun tokenFlow(): Flow<TokenPreferences?> {
        return dataStore.data.map { preferences ->
            val authToken = preferences[TokenPreferencesKeys.ACCESS_TOKEN]
            val refreshToken = preferences[TokenPreferencesKeys.REFRESH_TOKEN]
            val userId = preferences[TokenPreferencesKeys.USER_ID]

            if (authToken == null || refreshToken == null || userId == null) {
                null
            } else {
                TokenPreferences(
                    accessToken = authToken,
                    refreshToken = refreshToken,
                    userId = userId,
                )
            }
        }
    }
}
