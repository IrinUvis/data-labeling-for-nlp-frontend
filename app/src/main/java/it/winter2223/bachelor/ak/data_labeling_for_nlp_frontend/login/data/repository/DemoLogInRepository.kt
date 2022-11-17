package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.data.repository

import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.data.model.dto.RefreshTokenInput
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.data.model.dto.RefreshTokenOutput
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.data.model.dto.UserInput
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.data.model.dto.UserOutput
import kotlinx.coroutines.delay
import javax.inject.Inject

class DemoLogInRepository @Inject constructor() : LogInRepository {
    override suspend fun logIn(userInput: UserInput): Result<UserOutput> {
        @Suppress("MagicNumber")
        delay(2000)
        return Result.success(
            UserOutput(
                email = "demo@edu.p.lodz.pl",
                idToken = "authToken",
                refreshToken = "refreshToken",
            )
        )
    }

    override suspend fun signUp(userInput: UserInput): Result<UserOutput> {
        @Suppress("MagicNumber")
        delay(2000)
        return Result.success(
            UserOutput(
                email = "demo@edu.p.lodz.pl",
                idToken = "authToken",
                refreshToken = "refreshToken",
            )
        )
    }

    override suspend fun refreshToken(refreshTokenInput: RefreshTokenInput): Result<RefreshTokenOutput> {
        @Suppress("MagicNumber")
        delay(2000)
        return Result.success(
            RefreshTokenOutput(
                idToken = "refreshedAuthToken",
                refreshToken = "refreshedRefreshToken",
            )
        )
    }
}
