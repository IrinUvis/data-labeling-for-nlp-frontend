package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.domain.usecase

import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.domain.model.AuthenticationActivity
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.domain.model.Credentials
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.domain.model.LogInResult

interface CredentialsLogInOrSignUpUseCase {
    suspend operator fun invoke(
        credentials: Credentials,
        authenticationActivity: AuthenticationActivity,
    ): LogInResult
}
