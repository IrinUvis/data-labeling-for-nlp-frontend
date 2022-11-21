package it.winter2223.bachelor.ak.frontend.domain.authentication.usecase

import it.winter2223.bachelor.ak.frontend.domain.authentication.model.AuthenticationActivity
import it.winter2223.bachelor.ak.frontend.domain.authentication.model.Credentials
import it.winter2223.bachelor.ak.frontend.domain.authentication.model.LogInResult

interface CredentialsLogInOrSignUpUseCase {
    suspend operator fun invoke(
        credentials: Credentials,
        authenticationActivity: AuthenticationActivity,
    ): LogInResult
}
