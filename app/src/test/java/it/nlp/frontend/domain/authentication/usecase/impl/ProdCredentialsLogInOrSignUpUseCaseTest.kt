@file:Suppress("MaxLineLength")

package it.nlp.frontend.domain.authentication.usecase.impl

import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.mockk
import it.nlp.frontend.data.remote.authentication.model.dto.TokenOutput
import it.nlp.frontend.data.remote.authentication.model.dto.UserInput
import it.nlp.frontend.data.remote.authentication.model.dto.UserOutput
import it.nlp.frontend.data.remote.authentication.model.dto.UserRoleOutput
import it.nlp.frontend.data.remote.authentication.repository.AuthenticationRepository
import it.nlp.frontend.data.remote.model.ApiResponse
import it.nlp.frontend.data.remote.model.HttpStatus
import it.nlp.frontend.data.remote.model.exception.messages.SecurityExceptionMessage
import it.nlp.frontend.domain.authentication.model.AuthenticationActivity
import it.nlp.frontend.domain.authentication.model.Credentials
import it.nlp.frontend.domain.authentication.model.LogInResult
import it.nlp.frontend.domain.token.model.StoreTokenResult
import it.nlp.frontend.domain.token.model.Token
import it.nlp.frontend.domain.token.usecase.StoreTokenUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.io.IOException
import java.net.SocketTimeoutException

@OptIn(ExperimentalCoroutinesApi::class)
class ProdCredentialsLogInOrSignUpUseCaseTest {

    @Test
    fun useCase_invokedWithEmptyCredentials_returnsInvalidCredentialsLogInResult() = runTest {
        val mockAuthenticationRepository: AuthenticationRepository = mockk()
        val mockStoreTokenUseCase: StoreTokenUseCase = mockk()
        val useCase = ProdCredentialsLogInOrSignUpUseCase(
            authenticationRepository = mockAuthenticationRepository,
            storeTokenUseCase = mockStoreTokenUseCase
        )

        val result = useCase(
            credentials = Credentials(),
            authenticationActivity = AuthenticationActivity.LogIn,
        )

        val expectedResult = LogInResult.Failure.InvalidCredentials(
            emptyEmail = true,
            emptyPassword = true,
            passwordLessThanSixCharacters = true,
            badEmailFormat = true,
        )

        Truth.assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun useCase_invokedWithImproperEmailAddressAndPassword_returnsInvalidCredentialsLogInResult() =
        runTest {
            val mockAuthenticationRepository: AuthenticationRepository = mockk()
            val mockStoreTokenUseCase: StoreTokenUseCase = mockk()
            val useCase = ProdCredentialsLogInOrSignUpUseCase(
                authenticationRepository = mockAuthenticationRepository,
                storeTokenUseCase = mockStoreTokenUseCase
            )

            val result = useCase(
                credentials = Credentials(
                    email = "test@test",
                    password = "test"
                ),
                authenticationActivity = AuthenticationActivity.LogIn,
            )

            val expectedResult = LogInResult.Failure.InvalidCredentials(
                emptyEmail = false,
                emptyPassword = false,
                passwordLessThanSixCharacters = true,
                badEmailFormat = true,
            )

            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun useCase_invokedWithCorrectDataWithRepositoryReturningSuccessAndStoreTokenRepositoryReturningSuccess_returnsSuccessLogInResult() =
        runTest {
            val passedEmail = "test@test.com"
            val passedPassword = "password"
            val userId = "userId"
            val expiresIn = 2137L
            val accessToken = "accessToken"
            val refreshToken = "refreshToken"
            val userRoleOutput = UserRoleOutput.Admin

            val passedUserInput = UserInput(
                email = passedEmail,
                password = passedPassword,
            )
            val retrievedUserOutput = UserOutput(
                email = passedEmail,
                userId = userId,
                userRoleOutput = userRoleOutput,
                accessTokenOutput = TokenOutput(
                    value = accessToken,
                    expiresIn = expiresIn,
                ),
                refreshTokenOutput = TokenOutput(
                    value = refreshToken,
                    expiresIn = expiresIn
                )
            )
            val tokenToStore = Token(
                accessToken = accessToken,
                refreshToken = refreshToken,
                userId = userId,
            )
            val passedCredentials = Credentials(
                email = passedEmail,
                password = passedPassword
            )

            val mockAuthenticationRepository: AuthenticationRepository = mockk()
            val mockStoreTokenUseCase: StoreTokenUseCase = mockk()

            coEvery {
                mockAuthenticationRepository.logIn(passedUserInput)
            } returns ApiResponse.Success(retrievedUserOutput)
            coEvery {
                mockStoreTokenUseCase(tokenToStore)
            } returns StoreTokenResult.Success

            val useCase = ProdCredentialsLogInOrSignUpUseCase(
                authenticationRepository = mockAuthenticationRepository,
                storeTokenUseCase = mockStoreTokenUseCase
            )

            val result = useCase(
                credentials = passedCredentials,
                authenticationActivity = AuthenticationActivity.LogIn,
            )

            val expectedResult = LogInResult.Success

            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun useCase_invokedWithCorrectDataWithRepositoryReturningSuccessAndStoreTokenRepositoryReturningFailure_returnsUnexpectedLogInFailure() =
        runTest {
            val passedEmail = "test@test.com"
            val passedPassword = "password"
            val userId = "userId"
            val expiresIn = 2137L
            val accessToken = "accessToken"
            val refreshToken = "refreshToken"
            val userRoleOutput = UserRoleOutput.Admin

            val passedUserInput = UserInput(
                email = passedEmail,
                password = passedPassword,
            )
            val retrievedUserOutput = UserOutput(
                email = passedEmail,
                userId = userId,
                userRoleOutput = userRoleOutput,
                accessTokenOutput = TokenOutput(
                    value = accessToken,
                    expiresIn = expiresIn,
                ),
                refreshTokenOutput = TokenOutput(
                    value = refreshToken,
                    expiresIn = expiresIn
                )
            )
            val tokenToStore = Token(
                accessToken = accessToken,
                refreshToken = refreshToken,
                userId = userId,
            )
            val passedCredentials = Credentials(
                email = passedEmail,
                password = passedPassword
            )

            val mockAuthenticationRepository: AuthenticationRepository = mockk()
            val mockStoreTokenUseCase: StoreTokenUseCase = mockk()

            coEvery {
                mockAuthenticationRepository.logIn(passedUserInput)
            } returns ApiResponse.Success(retrievedUserOutput)
            coEvery {
                mockStoreTokenUseCase(tokenToStore)
            } returns StoreTokenResult.Failure(IOException())

            val useCase = ProdCredentialsLogInOrSignUpUseCase(
                authenticationRepository = mockAuthenticationRepository,
                storeTokenUseCase = mockStoreTokenUseCase
            )

            val result = useCase(
                credentials = passedCredentials,
                authenticationActivity = AuthenticationActivity.LogIn,
            )

            val expectedResult = LogInResult.Failure.Unexpected

            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun useCase_invokedWithCorrectDataWithRepositoryReturningSocketTimeoutException_returnsNetworkLogInFailure() =
        runTest {
            val passedEmail = "test@test.com"
            val passedPassword = "password"

            val passedUserInput = UserInput(
                email = passedEmail,
                password = passedPassword,
            )
            val passedCredentials = Credentials(
                email = passedEmail,
                password = passedPassword
            )

            val mockAuthenticationRepository: AuthenticationRepository = mockk()
            val mockStoreTokenUseCase: StoreTokenUseCase = mockk()

            coEvery {
                mockAuthenticationRepository.signUp(passedUserInput)
            } returns ApiResponse.Exception(SocketTimeoutException())

            val useCase = ProdCredentialsLogInOrSignUpUseCase(
                authenticationRepository = mockAuthenticationRepository,
                storeTokenUseCase = mockStoreTokenUseCase
            )

            val result = useCase(
                credentials = passedCredentials,
                authenticationActivity = AuthenticationActivity.SignUp,
            )

            val expectedResult = LogInResult.Failure.Network

            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun useCase_invokedWithCorrectDataWithRepositoryReturningServiceUnavailableCode_returnsServiceUnavailableLogInFailure() =
        runTest {
            val passedEmail = "test@test.com"
            val passedPassword = "password"

            val passedUserInput = UserInput(
                email = passedEmail,
                password = passedPassword,
            )
            val passedCredentials = Credentials(
                email = passedEmail,
                password = passedPassword
            )

            val mockAuthenticationRepository: AuthenticationRepository = mockk()
            val mockStoreTokenUseCase: StoreTokenUseCase = mockk()

            coEvery {
                mockAuthenticationRepository.signUp(passedUserInput)
            } returns ApiResponse.Failure("", HttpStatus.ServiceUnavailable.code)

            val useCase = ProdCredentialsLogInOrSignUpUseCase(
                authenticationRepository = mockAuthenticationRepository,
                storeTokenUseCase = mockStoreTokenUseCase
            )

            val result = useCase(
                credentials = passedCredentials,
                authenticationActivity = AuthenticationActivity.SignUp,
            )

            val expectedResult = LogInResult.Failure.ServiceUnavailable

            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun useCase_invokedWithCorrectDataWithRepositoryReturningEmailAlreadyTakenFailure_returnsWrongCredentialsLogInFailure() =
        runTest {
            val passedEmail = "test@test.com"
            val passedPassword = "password"

            val passedUserInput = UserInput(
                email = passedEmail,
                password = passedPassword,
            )
            val passedCredentials = Credentials(
                email = passedEmail,
                password = passedPassword
            )

            val mockAuthenticationRepository: AuthenticationRepository = mockk()
            val mockStoreTokenUseCase: StoreTokenUseCase = mockk()

            coEvery {
                mockAuthenticationRepository.logIn(passedUserInput)
            } returns ApiResponse.Failure(
                SecurityExceptionMessage.EmailAlreadyTaken.message,
                HttpStatus.BadRequest.code
            )

            val useCase = ProdCredentialsLogInOrSignUpUseCase(
                authenticationRepository = mockAuthenticationRepository,
                storeTokenUseCase = mockStoreTokenUseCase
            )

            val result = useCase(
                credentials = passedCredentials,
                authenticationActivity = AuthenticationActivity.LogIn,
            )

            val expectedResult = LogInResult.Failure.EmailAlreadyTaken

            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun useCase_invokedWithCorrectDataWithRepositoryReturningBadCredentialsFailure_returnsWrongCredentialsLogInFailure() =
        runTest {
            val passedEmail = "test@test.com"
            val passedPassword = "password"

            val passedUserInput = UserInput(
                email = passedEmail,
                password = passedPassword,
            )
            val passedCredentials = Credentials(
                email = passedEmail,
                password = passedPassword
            )

            val mockAuthenticationRepository: AuthenticationRepository = mockk()
            val mockStoreTokenUseCase: StoreTokenUseCase = mockk()

            coEvery {
                mockAuthenticationRepository.signUp(passedUserInput)
            } returns ApiResponse.Failure(
                SecurityExceptionMessage.BadCredentials.message,
                HttpStatus.BadRequest.code
            )

            val useCase = ProdCredentialsLogInOrSignUpUseCase(
                authenticationRepository = mockAuthenticationRepository,
                storeTokenUseCase = mockStoreTokenUseCase
            )

            val result = useCase(
                credentials = passedCredentials,
                authenticationActivity = AuthenticationActivity.SignUp,
            )

            val expectedResult = LogInResult.Failure.WrongCredentials

            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun useCase_invokedWithCorrectDataWithRepositoryReturningInvalidEmailFailure_returnsInvalidCredentialsLogInFailure() =
        runTest {
            val passedEmail = "test@test.com"
            val passedPassword = "password"

            val passedUserInput = UserInput(
                email = passedEmail,
                password = passedPassword,
            )
            val passedCredentials = Credentials(
                email = passedEmail,
                password = passedPassword
            )

            val mockAuthenticationRepository: AuthenticationRepository = mockk()
            val mockStoreTokenUseCase: StoreTokenUseCase = mockk()

            coEvery {
                mockAuthenticationRepository.signUp(passedUserInput)
            } returns ApiResponse.Failure(
                SecurityExceptionMessage.InvalidEmailAddress.message,
                HttpStatus.BadRequest.code
            )

            val useCase = ProdCredentialsLogInOrSignUpUseCase(
                authenticationRepository = mockAuthenticationRepository,
                storeTokenUseCase = mockStoreTokenUseCase
            )

            val result = useCase(
                credentials = passedCredentials,
                authenticationActivity = AuthenticationActivity.SignUp,
            )

            val expectedResult = LogInResult.Failure.InvalidCredentials(badEmailFormat = true)

            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun useCase_invokedWithCorrectDataWithRepositoryReturningInvalidPasswordFailure_returnsInvalidCredentialsLogInFailure() =
        runTest {
            val passedEmail = "test@test.com"
            val passedPassword = "password"

            val passedUserInput = UserInput(
                email = passedEmail,
                password = passedPassword,
            )
            val passedCredentials = Credentials(
                email = passedEmail,
                password = passedPassword
            )

            val mockAuthenticationRepository: AuthenticationRepository = mockk()
            val mockStoreTokenUseCase: StoreTokenUseCase = mockk()

            coEvery {
                mockAuthenticationRepository.signUp(passedUserInput)
            } returns ApiResponse.Failure(
                SecurityExceptionMessage.InvalidPassword.message,
                HttpStatus.BadRequest.code
            )

            val useCase = ProdCredentialsLogInOrSignUpUseCase(
                authenticationRepository = mockAuthenticationRepository,
                storeTokenUseCase = mockStoreTokenUseCase
            )

            val result = useCase(
                credentials = passedCredentials,
                authenticationActivity = AuthenticationActivity.SignUp,
            )

            val expectedResult =
                LogInResult.Failure.InvalidCredentials(passwordLessThanSixCharacters = true)

            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun useCase_invokedWithCorrectDataWithRepositoryReturningUnknownFailure_returnsUnexpectedLogInFailure() =
        runTest {
            val passedEmail = "test@test.com"
            val passedPassword = "password"

            val passedUserInput = UserInput(
                email = passedEmail,
                password = passedPassword,
            )
            val passedCredentials = Credentials(
                email = passedEmail,
                password = passedPassword
            )

            val mockAuthenticationRepository: AuthenticationRepository = mockk()
            val mockStoreTokenUseCase: StoreTokenUseCase = mockk()

            coEvery {
                mockAuthenticationRepository.signUp(passedUserInput)
            } returns ApiResponse.Failure("", HttpStatus.BadRequest.code)

            val useCase = ProdCredentialsLogInOrSignUpUseCase(
                authenticationRepository = mockAuthenticationRepository,
                storeTokenUseCase = mockStoreTokenUseCase
            )

            val result = useCase(
                credentials = passedCredentials,
                authenticationActivity = AuthenticationActivity.SignUp,
            )

            val expectedResult = LogInResult.Failure.Unexpected

            Truth.assertThat(result).isEqualTo(expectedResult)
        }
}
