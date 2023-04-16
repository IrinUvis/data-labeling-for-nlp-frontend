package it.nlp.frontend.domain.authentication.usecase

import it.nlp.frontend.domain.authentication.model.AuthenticationActivity
import it.nlp.frontend.domain.authentication.model.Credentials
import it.nlp.frontend.domain.authentication.model.LogInResult

interface CredentialsLogInOrSignUpUseCase {
    suspend operator fun invoke(
        credentials: Credentials,
        authenticationActivity: AuthenticationActivity,
    ): LogInResult
}
