@file:Suppress("MaxLineLength")

package it.nlp.frontend.domain.authentication.usecase.impl

import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.mockk
import it.nlp.frontend.data.remote.authentication.model.dto.TokenOutput
import it.nlp.frontend.data.remote.authentication.model.dto.UserInput
import it.nlp.frontend.data.remote.authentication.model.dto.UserOutput
import it.nlp.frontend.data.remote.authentication.model.dto.UserRoleOutput
import it.nlp.frontend.data.remote.authentication.model.exception.AuthenticationException
import it.nlp.frontend.data.remote.authentication.repository.AuthenticationService
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

@OptIn(ExperimentalCoroutinesApi::class)
class ProdCredentialsLogInOrSignUpUseCaseTest {

    @Test
    fun useCase_invokedWithEmptyCredentials_returnsInvalidCredentialsLogInResult() = runTest {
        val mockAuthenticationRepository: AuthenticationService = mockk()
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
            val mockAuthenticationRepository: AuthenticationService = mockk()
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

            val mockAuthenticationRepository: AuthenticationService = mockk()
            val mockStoreTokenUseCase: StoreTokenUseCase = mockk()

            coEvery {
                mockAuthenticationRepository.logIn(passedUserInput)
            } returns DataResult.Success(retrievedUserOutput)
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
    fun useCase_invokedWithCorrectDataWithRepositoryReturningSuccessAndStoreTokenRepositoryReturningFailure_returnsDataStoreLogInFailure() =
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

            val mockAuthenticationRepository: AuthenticationService = mockk()
            val mockStoreTokenUseCase: StoreTokenUseCase = mockk()

            coEvery {
                mockAuthenticationRepository.logIn(passedUserInput)
            } returns DataResult.Success(retrievedUserOutput)
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

            val expectedResult = LogInResult.Failure.DataStore

            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun useCase_invokedWithCorrectDataWithRepositoryReturningNetworkApiException_returnsNetworkLogInFailure() =
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

            val mockAuthenticationRepository: AuthenticationService = mockk()
            val mockStoreTokenUseCase: StoreTokenUseCase = mockk()

            coEvery {
                mockAuthenticationRepository.signUp(passedUserInput)
            } returns DataResult.Failure(NetworkException(null, null))

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
    fun useCase_invokedWithCorrectDataWithRepositoryReturningServiceUnavailableApiException_returnsServiceUnavailableLogInFailure() =
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

            val mockAuthenticationRepository: AuthenticationService = mockk()
            val mockStoreTokenUseCase: StoreTokenUseCase = mockk()

            coEvery {
                mockAuthenticationRepository.signUp(passedUserInput)
            } returns DataResult.Failure(ServiceUnavailableException(null, null))

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
    fun useCase_invokedWithCorrectDataWithRepositoryReturningSigningInFailedException_returnsWrongCredentialsLogInFailure() =
        runTest {
            val passedEmail = "test@test.com"
            val passedPassword = "password"
            val errorMessage = "errorMessage"

            val passedUserInput = UserInput(
                email = passedEmail,
                password = passedPassword,
            )
            val passedCredentials = Credentials(
                email = passedEmail,
                password = passedPassword
            )

            val mockAuthenticationRepository: AuthenticationService = mockk()
            val mockStoreTokenUseCase: StoreTokenUseCase = mockk()

            coEvery {
                mockAuthenticationRepository.logIn(passedUserInput)
            } returns DataResult.Failure(AuthenticationException.EmailAlreadyTaken(errorMessage))

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
    fun useCase_invokedWithCorrectDataWithRepositoryReturningSigningUpFailedException_returnsWrongCredentialsLogInFailure() =
        runTest {
            val passedEmail = "test@test.com"
            val passedPassword = "password"
            val errorMessage = "errorMessage"

            val passedUserInput = UserInput(
                email = passedEmail,
                password = passedPassword,
            )
            val passedCredentials = Credentials(
                email = passedEmail,
                password = passedPassword
            )

            val mockAuthenticationRepository: AuthenticationService = mockk()
            val mockStoreTokenUseCase: StoreTokenUseCase = mockk()

            coEvery {
                mockAuthenticationRepository.signUp(passedUserInput)
            } returns DataResult.Failure(AuthenticationException.BadCredentials(errorMessage))

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
    fun useCase_invokedWithCorrectDataWithRepositoryReturningInvalidEmailException_returnsInvalidCredentialsLogInFailure() =
        runTest {
            val passedEmail = "test@test.com"
            val passedPassword = "password"
            val errorMessage = "errorMessage"

            val passedUserInput = UserInput(
                email = passedEmail,
                password = passedPassword,
            )
            val passedCredentials = Credentials(
                email = passedEmail,
                password = passedPassword
            )

            val mockAuthenticationRepository: AuthenticationService = mockk()
            val mockStoreTokenUseCase: StoreTokenUseCase = mockk()

            coEvery {
                mockAuthenticationRepository.signUp(passedUserInput)
            } returns DataResult.Failure(AuthenticationException.InvalidEmailAddress(errorMessage))

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
    fun useCase_invokedWithCorrectDataWithRepositoryReturningInvalidPasswordException_returnsInvalidCredentialsLogInFailure() =
        runTest {
            val passedEmail = "test@test.com"
            val passedPassword = "password"
            val errorMessage = "errorMessage"

            val passedUserInput = UserInput(
                email = passedEmail,
                password = passedPassword,
            )
            val passedCredentials = Credentials(
                email = passedEmail,
                password = passedPassword
            )

            val mockAuthenticationRepository: AuthenticationService = mockk()
            val mockStoreTokenUseCase: StoreTokenUseCase = mockk()

            coEvery {
                mockAuthenticationRepository.signUp(passedUserInput)
            } returns DataResult.Failure(AuthenticationException.InvalidPassword(errorMessage))

            val useCase = ProdCredentialsLogInOrSignUpUseCase(
                authenticationRepository = mockAuthenticationRepository,
                storeTokenUseCase = mockStoreTokenUseCase
            )

            val result = useCase(
                credentials = passedCredentials,
                authenticationActivity = AuthenticationActivity.SignUp,
            )

            val expectedResult = LogInResult.Failure.InvalidCredentials(passwordLessThanSixCharacters = true)

            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun useCase_invokedWithCorrectDataWithRepositoryReturningUnknownException_returnsUnknownLogInFailure() =
        runTest {
            val passedEmail = "test@test.com"
            val passedPassword = "password"
            val errorMessage = "errorMessage"

            val passedUserInput = UserInput(
                email = passedEmail,
                password = passedPassword,
            )
            val passedCredentials = Credentials(
                email = passedEmail,
                password = passedPassword
            )

            val mockAuthenticationRepository: AuthenticationService = mockk()
            val mockStoreTokenUseCase: StoreTokenUseCase = mockk()

            coEvery {
                mockAuthenticationRepository.signUp(passedUserInput)
            } returns DataResult.Failure(AuthenticationException.Unknown(errorMessage))

            val useCase = ProdCredentialsLogInOrSignUpUseCase(
                authenticationRepository = mockAuthenticationRepository,
                storeTokenUseCase = mockStoreTokenUseCase
            )

            val result = useCase(
                credentials = passedCredentials,
                authenticationActivity = AuthenticationActivity.SignUp,
            )

            val expectedResult = LogInResult.Failure.Unknown

            Truth.assertThat(result).isEqualTo(expectedResult)
        }
}
