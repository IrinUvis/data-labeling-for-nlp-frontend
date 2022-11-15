package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.data.usecase

import android.util.Patterns
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.data.model.Credentials
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.data.model.exception.WrongCredentialsException
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.data.model.LogInResult
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.data.model.Token
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.data.model.dto.UserInput
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.data.repository.LoginRepository
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.data.repository.TokenRepository
import javax.inject.Inject

class ProdCredentialsLogInOrSignUpUseCase @Inject constructor(
    private val tokenRepository: TokenRepository,
    private val loginRepository: LoginRepository,
) : CredentialsLogInOrSignUpUseCase {
    companion object {
        private const val MIN_PASSWORD_SIZE = 6
    }

    override suspend fun invoke(
        credentials: Credentials,
        authenticationActivity: AuthenticationActivity,
    ): LogInResult {
        val validationResult = validateCredentials(credentials)

        if (validationResult != null) {
            return validationResult
        }

        val repoResult = when (authenticationActivity) {
            AuthenticationActivity.LogIn -> loginRepository.logIn(UserInput(credentials.email,
                credentials.password))
            AuthenticationActivity.SignUp -> loginRepository.signUp(UserInput(credentials.email,
                credentials.password))
        }

        return repoResult.fold(
            onSuccess = {
                tokenRepository.storeToken(
                    Token(
                        authToken = it.idToken,
                        refreshToken = it.refreshToken,
                    ),
                )
                LogInResult.Success
            },
            onFailure = { throwable ->
                logInResultForThrowable(throwable)
            }
        )
    }

    private fun validateCredentials(credentials: Credentials): LogInResult.Failure? {
        val emptyEmail = credentials.email.isEmpty()
        val emptyPassword = credentials.password.isEmpty()
        val emailConformsToRegex = Patterns.EMAIL_ADDRESS.matcher(credentials.email).matches()
        val passwordAtLeastSixCharacters = credentials.password.length >= MIN_PASSWORD_SIZE
        val credentialsInvalid = !emailConformsToRegex
                || !passwordAtLeastSixCharacters
                || emptyEmail
                || emptyPassword

        return if (credentialsInvalid) {
            LogInResult.Failure.InvalidCredentials(
                badEmailFormat = !emailConformsToRegex,
                passwordLessThanSixCharacters = !passwordAtLeastSixCharacters,
                emptyEmail = emptyEmail,
                emptyPassword = emptyPassword,
            )
        } else {
            null
        }
    }

    private fun logInResultForThrowable(throwable: Throwable): LogInResult.Failure {
        return when (throwable) {
            is WrongCredentialsException -> {
                LogInResult.Failure.WrongCredentials
            }
            else -> {
                LogInResult.Failure.Unknown
            }
        }
    }
}
