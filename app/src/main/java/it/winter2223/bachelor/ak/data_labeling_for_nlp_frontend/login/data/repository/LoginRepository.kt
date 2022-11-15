package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.data.repository

import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.data.model.Credentials
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.data.model.dto.RefreshTokenOutput
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.data.model.dto.UserOutput

interface LoginRepository {
    suspend fun logIn(credentials: Credentials): Result<UserOutput>

    suspend fun signUp(credentials: Credentials): Result<UserOutput>

    suspend fun refreshToken(refreshToken: String): Result<RefreshTokenOutput>
}
