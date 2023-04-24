package it.nlp.frontend.domain.authentication.usecase.impl

import it.nlp.frontend.data.remote.authentication.model.dto.UserInput
import it.nlp.frontend.data.remote.authentication.repository.impl.NetworkAuthenticationRepository
import it.nlp.frontend.data.remote.model.ApiResponse
import it.nlp.frontend.data.remote.model.HttpStatus
import it.nlp.frontend.data.remote.model.exception.messages.SecurityExceptionMessage
import it.nlp.frontend.domain.authentication.model.AuthenticationActivity
import it.nlp.frontend.domain.authentication.model.Credentials
import it.nlp.frontend.domain.authentication.model.LogInResult
import it.nlp.frontend.domain.authentication.usecase.CredentialsLogInOrSignUpUseCase
import it.nlp.frontend.domain.token.model.StoreTokenResult
import it.nlp.frontend.domain.token.model.Token
import it.nlp.frontend.domain.token.usecase.StoreTokenUseCase
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.util.regex.Pattern
import javax.inject.Inject

class ProdCredentialsLogInOrSignUpUseCase @Inject constructor(
    private val authenticationRepository: NetworkAuthenticationRepository,
    private val storeTokenUseCase: StoreTokenUseCase,
) : CredentialsLogInOrSignUpUseCase {
    companion object {
        private const val MIN_PASSWORD_SIZE = 6
        private val EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9+._%-+]{1,256}" +
                    "@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
        )
    }

    override suspend fun invoke(
        credentials: Credentials,
        authenticationActivity: AuthenticationActivity,
    ): LogInResult {
        val validationResult = validateCredentials(credentials)
        if (validationResult != null) {
            return validationResult
        }

        val userInput = UserInput(credentials.email, credentials.password)
        val apiResponse = when (authenticationActivity) {
            AuthenticationActivity.LogIn -> authenticationRepository.logIn(userInput)
            AuthenticationActivity.SignUp -> authenticationRepository.signUp(userInput)
        }

        return when (apiResponse) {
            is ApiResponse.Success -> {
                val userOutput = apiResponse.data
                val storeTokenResult = storeTokenUseCase(
                    Token(
                        accessToken = userOutput.accessTokenOutput.value,
                        refreshToken = userOutput.refreshTokenOutput.value,
                        userId = userOutput.userId,
                    )
                )
                when (storeTokenResult) {
                    is StoreTokenResult.Success -> LogInResult.Success
                    is StoreTokenResult.Failure -> LogInResult.Failure.Unexpected
                }
            }

            is ApiResponse.Failure -> logInResultForApiResponseFailure(apiResponse)
            is ApiResponse.Exception -> logInResultForApiResponseException(apiResponse)
        }
    }

    private fun validateCredentials(credentials: Credentials): LogInResult.Failure? {
        val emptyEmail = credentials.email.isEmpty()
        val emptyPassword = credentials.password.isEmpty()
        val emailConformsToRegex = EMAIL_ADDRESS_PATTERN.matcher(credentials.email).matches()
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

    private fun logInResultForApiResponseFailure(
        apiFailureResponse: ApiResponse.Failure
    ): LogInResult.Failure {
        return when (apiFailureResponse.code) {
            HttpStatus.ServiceUnavailable.code, HttpStatus.GatewayTimeout.code -> LogInResult.Failure.ServiceUnavailable
            HttpStatus.BadRequest.code -> {
                val message = apiFailureResponse.errorMessage
                when {
                    message.contains(SecurityExceptionMessage.InvalidEmailAddress.message) ->
                        LogInResult.Failure.InvalidCredentials(badEmailFormat = true)

                    message.contains(SecurityExceptionMessage.InvalidPassword.message) ->
                        LogInResult.Failure.InvalidCredentials(passwordLessThanSixCharacters = true)

                    message.contains(SecurityExceptionMessage.EmailAlreadyTaken.message) ->
                        LogInResult.Failure.EmailAlreadyTaken

                    message.contains(SecurityExceptionMessage.BadCredentials.message) ->
                        LogInResult.Failure.WrongCredentials

                    else -> LogInResult.Failure.Unexpected
                }
            }

            else -> LogInResult.Failure.Unknown
        }
    }

    private fun logInResultForApiResponseException(
        apiResponseException: ApiResponse.Exception
    ): LogInResult.Failure {
        return when (apiResponseException.exception) {
            is ConnectException, is SocketTimeoutException -> LogInResult.Failure.Network
            else -> LogInResult.Failure.Unexpected
        }
    }
}
