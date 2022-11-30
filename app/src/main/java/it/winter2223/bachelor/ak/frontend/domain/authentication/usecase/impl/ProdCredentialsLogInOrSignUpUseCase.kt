package it.winter2223.bachelor.ak.frontend.domain.authentication.usecase.impl

import android.util.Patterns
import it.winter2223.bachelor.ak.frontend.domain.authentication.model.AuthenticationActivity
import it.winter2223.bachelor.ak.frontend.domain.authentication.model.Credentials
import it.winter2223.bachelor.ak.frontend.data.remote.authentication.model.exception.WrongCredentialsException
import it.winter2223.bachelor.ak.frontend.domain.authentication.model.LogInResult
import it.winter2223.bachelor.ak.frontend.data.remote.authentication.model.dto.UserInput
import it.winter2223.bachelor.ak.frontend.data.remote.authentication.repository.LogInRepository
import it.winter2223.bachelor.ak.frontend.domain.token.model.StoreTokenResult
import it.winter2223.bachelor.ak.frontend.domain.token.model.Token
import it.winter2223.bachelor.ak.frontend.domain.authentication.usecase.CredentialsLogInOrSignUpUseCase
import it.winter2223.bachelor.ak.frontend.domain.token.usecase.StoreTokenUseCase
import javax.inject.Inject

class ProdCredentialsLogInOrSignUpUseCase @Inject constructor(
    private val logInRepository: LogInRepository,
    private val storeTokenUseCase: StoreTokenUseCase,
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
            AuthenticationActivity.LogIn -> logInRepository.logIn(
                UserInput(
                    credentials.email,
                    credentials.password,
                ),
            )
            AuthenticationActivity.SignUp -> logInRepository.signUp(
                UserInput(
                    credentials.email,
                    credentials.password,
                ),
            )
        }

        return repoResult.fold(
            onSuccess = {
                val storeTokenResult = storeTokenUseCase(
                    Token(
                        authToken = it.idToken,
                        refreshToken = it.refreshToken,
                        userId = it.userId,
                    )
                )
                when (storeTokenResult) {
                    is StoreTokenResult.Success -> LogInResult.Success
                    is StoreTokenResult.Failure -> LogInResult.Failure.DataStore
                }
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
