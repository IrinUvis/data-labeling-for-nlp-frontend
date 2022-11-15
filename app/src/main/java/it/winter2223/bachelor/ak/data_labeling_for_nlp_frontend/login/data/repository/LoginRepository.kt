package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.data.repository

import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.data.model.dto.RefreshTokenInput
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.data.model.dto.RefreshTokenOutput
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.data.model.dto.UserInput
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.data.model.dto.UserOutput

interface LoginRepository {
    suspend fun logIn(userInput: UserInput): Result<UserOutput>

    suspend fun signUp(userInput: UserInput): Result<UserOutput>

    suspend fun refreshToken(refreshTokenInput: RefreshTokenInput): Result<RefreshTokenOutput>
}
