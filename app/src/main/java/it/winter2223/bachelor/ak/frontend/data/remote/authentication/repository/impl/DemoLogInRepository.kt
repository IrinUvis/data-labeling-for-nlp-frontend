package it.winter2223.bachelor.ak.frontend.data.remote.authentication.repository.impl

import it.winter2223.bachelor.ak.frontend.data.remote.authentication.model.dto.RefreshTokenInput
import it.winter2223.bachelor.ak.frontend.data.remote.authentication.model.dto.RefreshTokenOutput
import it.winter2223.bachelor.ak.frontend.data.remote.authentication.model.dto.UserInput
import it.winter2223.bachelor.ak.frontend.data.remote.authentication.model.dto.UserOutput
import it.winter2223.bachelor.ak.frontend.data.remote.authentication.repository.LogInRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class DemoLogInRepository @Inject constructor() : LogInRepository {
    override suspend fun logIn(userInput: UserInput): Result<UserOutput> {
        @Suppress("MagicNumber")
        delay(2000)
        return Result.success(
            UserOutput(
                email = "demo@edu.p.lodz.pl",
                userId = "userId",
                authToken = "authToken",
                expiresIn = "whatever",
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
                userId = "userId",
                authToken = "authToken",
                expiresIn = "whatever",
                refreshToken = "refreshToken",
            )
        )
    }

    override suspend fun refreshToken(refreshTokenInput: RefreshTokenInput): Result<RefreshTokenOutput> {
        @Suppress("MagicNumber")
        delay(2000)
        return Result.success(
            RefreshTokenOutput(
                userId = "userId",
                authToken = "refreshedAuthToken",
                expiresIn = "whatever",
                refreshToken = "refreshedRefreshToken",
            )
        )
    }
}
