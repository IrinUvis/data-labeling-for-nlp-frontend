package it.winter2223.bachelor.ak.frontend.domain.authentication.usecase.impl

import android.util.Patterns
import it.winter2223.bachelor.ak.frontend.domain.authentication.model.AuthenticationActivity
import it.winter2223.bachelor.ak.frontend.domain.authentication.model.Credentials
import it.winter2223.bachelor.ak.frontend.data.remote.authentication.model.exception.AuthenticationException
import it.winter2223.bachelor.ak.frontend.domain.authentication.model.LogInResult
import it.winter2223.bachelor.ak.frontend.data.remote.authentication.model.dto.UserInput
import it.winter2223.bachelor.ak.frontend.data.remote.authentication.repository.AuthenticationRepository
import it.winter2223.bachelor.ak.frontend.data.remote.model.exception.ApiException
import it.winter2223.bachelor.ak.frontend.data.remote.model.exception.NetworkException
import it.winter2223.bachelor.ak.frontend.domain.token.model.StoreTokenResult
import it.winter2223.bachelor.ak.frontend.domain.token.model.Token
import it.winter2223.bachelor.ak.frontend.domain.authentication.usecase.CredentialsLogInOrSignUpUseCase
import it.winter2223.bachelor.ak.frontend.domain.token.usecase.StoreTokenUseCase
import javax.inject.Inject

class ProdCredentialsLogInOrSignUpUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
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
            AuthenticationActivity.LogIn -> authenticationRepository.logIn(
                UserInput(
                    credentials.email,
                    credentials.password,
                ),
            )
            AuthenticationActivity.SignUp -> authenticationRepository.signUp(
                UserInput(
                    credentials.email,
                    credentials.password,
                ),
            )
        }

        return repoResult.fold(
            onSuccess = { userOutput ->
                val storeTokenResult = storeTokenUseCase(
                    Token(
                        authToken = userOutput.authToken,
                        refreshToken = userOutput.refreshToken,
                        userId = userOutput.userId,
                    )
                )
                when (storeTokenResult) {
                    is StoreTokenResult.Success -> LogInResult.Success
                    is StoreTokenResult.Failure -> LogInResult.Failure.DataStore
                }
            },
            onFailure = { apiException ->
                logInResultForApiException(apiException)
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

    private fun logInResultForApiException(exception: ApiException): LogInResult.Failure {
        return when (exception) {
            is NetworkException -> LogInResult.Failure.NoInternet
            is AuthenticationException.SigningInFailed -> LogInResult.Failure.WrongCredentials
            is AuthenticationException.SigningUpFailed -> LogInResult.Failure.WrongCredentials
            is AuthenticationException.InvalidEmailAddress -> {
                LogInResult.Failure.InvalidCredentials(
                    badEmailFormat = true
                )
            }
            is AuthenticationException.InvalidPassword -> {
                LogInResult.Failure.InvalidCredentials(
                    passwordLessThanSixCharacters = true
                )
            }
            else -> LogInResult.Failure.Unknown
        }
    }
}
