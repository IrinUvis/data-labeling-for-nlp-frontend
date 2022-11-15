package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.data.repository

import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.data.model.Credentials
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.data.model.LoginException
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.data.model.dto.RefreshTokenOutput
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.data.model.dto.UserOutput
import kotlinx.coroutines.delay
import javax.inject.Inject

class DemoLoginRepository @Inject constructor() : LoginRepository {
    override suspend fun logIn(credentials: Credentials): Result<UserOutput> {
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

    override suspend fun signUp(credentials: Credentials): Result<UserOutput> {
        @Suppress("MagicNumber")
        delay(2000)
        return if (credentials.email.isEmpty()) {
            Result.failure(LoginException.EmptyCredentialsException())
        } else {
            Result.failure(LoginException.InvalidCredentialsException())
        }
    }

    override suspend fun refreshToken(refreshToken: String): Result<RefreshTokenOutput> {
        @Suppress("MagicNumber")
        delay(2000)
        return Result.success(
            RefreshTokenOutput(
                authToken = "refreshedAuthToken",
                refreshToken = "refreshedRefreshToken",
            )
        )
    }
}