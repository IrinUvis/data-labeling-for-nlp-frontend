package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.data.usecase

import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.data.model.Credentials
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.data.model.LogInResult

interface CredentialsLogInOrSignUpUseCase {
    suspend operator fun invoke(
        credentials: Credentials,
        authenticationActivity: AuthenticationActivity,
    ): LogInResult
}

enum class AuthenticationActivity {
    LogIn,
    SignUp,
}
