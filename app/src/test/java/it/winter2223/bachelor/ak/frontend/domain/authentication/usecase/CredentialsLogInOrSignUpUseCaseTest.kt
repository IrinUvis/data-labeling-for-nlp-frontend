package it.winter2223.bachelor.ak.frontend.domain.authentication.usecase

import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.mockk
import it.winter2223.bachelor.ak.frontend.data.core.model.DataResult
import it.winter2223.bachelor.ak.frontend.data.remote.authentication.model.dto.UserInput
import it.winter2223.bachelor.ak.frontend.data.remote.authentication.model.dto.UserOutput
import it.winter2223.bachelor.ak.frontend.data.remote.authentication.model.exception.AuthenticationException
import it.winter2223.bachelor.ak.frontend.data.remote.authentication.repository.AuthenticationRepository
import it.winter2223.bachelor.ak.frontend.data.remote.model.exception.NetworkException
import it.winter2223.bachelor.ak.frontend.domain.authentication.model.AuthenticationActivity
import it.winter2223.bachelor.ak.frontend.domain.authentication.model.Credentials
import it.winter2223.bachelor.ak.frontend.domain.authentication.model.LogInResult
import it.winter2223.bachelor.ak.frontend.domain.authentication.usecase.impl.ProdCredentialsLogInOrSignUpUseCase
import it.winter2223.bachelor.ak.frontend.domain.token.model.StoreTokenResult
import it.winter2223.bachelor.ak.frontend.domain.token.model.Token
import it.winter2223.bachelor.ak.frontend.domain.token.usecase.StoreTokenUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CredentialsLogInOrSignUpUseCaseTest {

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
            val expiresIn = "expiresIn"
            val authToken = "authToken"
            val refreshToken = "refreshToken"

            val passedUserInput = UserInput(
                email = passedEmail,
                password = passedPassword,
            )
            val retrievedUserOutput = UserOutput(
                email = passedEmail,
                userId = userId,
                expiresIn = expiresIn,
                authToken = authToken,
                refreshToken = refreshToken,
            )
            val tokenToStore = Token(
                authToken = authToken,
                refreshToken = refreshToken,
                userId = userId,
            )
            val passedCredentials = Credentials(
                email = passedEmail,
                password = passedPassword
            )

            val mockAuthenticationRepository: AuthenticationRepository = mockk()
            coEvery {
                mockAuthenticationRepository.logIn(passedUserInput)
            } returns DataResult.Success(retrievedUserOutput)

            val mockStoreTokenUseCase: StoreTokenUseCase = mockk()
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
            val expiresIn = "expiresIn"
            val authToken = "authToken"
            val refreshToken = "refreshToken"

            val passedUserInput = UserInput(
                email = passedEmail,
                password = passedPassword,
            )
            val retrievedUserOutput = UserOutput(
                email = passedEmail,
                userId = userId,
                expiresIn = expiresIn,
                authToken = authToken,
                refreshToken = refreshToken,
            )
            val tokenToStore = Token(
                authToken = authToken,
                refreshToken = refreshToken,
                userId = userId,
            )
            val passedCredentials = Credentials(
                email = passedEmail,
                password = passedPassword
            )

            val mockAuthenticationRepository: AuthenticationRepository = mockk()
            coEvery {
                mockAuthenticationRepository.logIn(passedUserInput)
            } returns DataResult.Success(retrievedUserOutput)

            val mockStoreTokenUseCase: StoreTokenUseCase = mockk()
            coEvery {
                mockStoreTokenUseCase(tokenToStore)
            } returns StoreTokenResult.Failure

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

            val mockAuthenticationRepository: AuthenticationRepository = mockk()
            coEvery {
                mockAuthenticationRepository.signUp(passedUserInput)
            } returns DataResult.Failure(NetworkException(null, null))

            val mockStoreTokenUseCase: StoreTokenUseCase = mockk()

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
    fun useCase_invokedWithCorrectDataWithRepositoryReturningSigningInFailedException_returnsWrongCredentialsLogInFailure() =
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
            coEvery {
                mockAuthenticationRepository.logIn(passedUserInput)
            } returns DataResult.Failure(AuthenticationException.SigningInFailed("errorMessage"))

            val mockStoreTokenUseCase: StoreTokenUseCase = mockk()

            val useCase = ProdCredentialsLogInOrSignUpUseCase(
                authenticationRepository = mockAuthenticationRepository,
                storeTokenUseCase = mockStoreTokenUseCase
            )

            val result = useCase(
                credentials = passedCredentials,
                authenticationActivity = AuthenticationActivity.LogIn,
            )

            val expectedResult = LogInResult.Failure.WrongCredentials

            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun useCase_invokedWithCorrectDataWithRepositoryReturningSigningUpFailedException_returnsWrongCredentialsLogInFailure() =
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
            coEvery {
                mockAuthenticationRepository.signUp(passedUserInput)
            } returns DataResult.Failure(AuthenticationException.SigningUpFailed("errorMessage"))

            val mockStoreTokenUseCase: StoreTokenUseCase = mockk()

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

            val passedUserInput = UserInput(
                email = passedEmail,
                password = passedPassword,
            )
            val passedCredentials = Credentials(
                email = passedEmail,
                password = passedPassword
            )

            val mockAuthenticationRepository: AuthenticationRepository = mockk()
            coEvery {
                mockAuthenticationRepository.signUp(passedUserInput)
            } returns DataResult.Failure(AuthenticationException.InvalidEmailAddress("errorMessage"))

            val mockStoreTokenUseCase: StoreTokenUseCase = mockk()

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

            val passedUserInput = UserInput(
                email = passedEmail,
                password = passedPassword,
            )
            val passedCredentials = Credentials(
                email = passedEmail,
                password = passedPassword
            )

            val mockAuthenticationRepository: AuthenticationRepository = mockk()
            coEvery {
                mockAuthenticationRepository.signUp(passedUserInput)
            } returns DataResult.Failure(AuthenticationException.InvalidPassword("errorMessage"))

            val mockStoreTokenUseCase: StoreTokenUseCase = mockk()

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

            val passedUserInput = UserInput(
                email = passedEmail,
                password = passedPassword,
            )
            val passedCredentials = Credentials(
                email = passedEmail,
                password = passedPassword
            )

            val mockAuthenticationRepository: AuthenticationRepository = mockk()
            coEvery {
                mockAuthenticationRepository.signUp(passedUserInput)
            } returns DataResult.Failure(AuthenticationException.Unknown("errorMessage"))

            val mockStoreTokenUseCase: StoreTokenUseCase = mockk()

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