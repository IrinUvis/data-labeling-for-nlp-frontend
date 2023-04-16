package it.nlp.frontend.domain.token.usecase.impl

import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.mockk
import it.winter2223.bachelor.ak.frontend.data.local.token.model.TokenPreferences
import it.winter2223.bachelor.ak.frontend.data.local.token.repository.TokenRepository
import it.winter2223.bachelor.ak.frontend.domain.token.model.StoreTokenResult
import it.winter2223.bachelor.ak.frontend.domain.token.model.Token
import it.winter2223.bachelor.ak.frontend.domain.token.usecase.impl.ProdStoreTokenUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class ProdStoreTokenUseCaseTest {

    @Test
    fun useCase_invokedWithRepositoryReturningUnit_returnsSuccess() = runTest {
        val authToken = "authToken"
        val refreshToken = "refreshToken"
        val userId = "userId"
        val tokenPreferences = TokenPreferences(
            authToken = authToken,
            refreshToken = refreshToken,
            userId = userId,
        )
        val token = Token(
            authToken = authToken,
            refreshToken = refreshToken,
            userId = userId,
        )

        val tokenRepository: TokenRepository = mockk()

        coEvery { tokenRepository.storeToken(tokenPreferences) } returns Unit

        val useCase = ProdStoreTokenUseCase(
            tokenRepository = tokenRepository,
        )

        val expectedResult = StoreTokenResult.Success

        val result = useCase(token)

        Truth.assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun useCase_invokedWithRepositoryThrowingIOException_returnsSuccess() = runTest {
        val authToken = "authToken"
        val refreshToken = "refreshToken"
        val userId = "userId"
        val tokenPreferences = TokenPreferences(
            authToken = authToken,
            refreshToken = refreshToken,
            userId = userId,
        )
        val token = Token(
            authToken = authToken,
            refreshToken = refreshToken,
            userId = userId,
        )

        val tokenRepository: TokenRepository = mockk()

        coEvery { tokenRepository.storeToken(tokenPreferences) } throws IOException()

        val useCase = ProdStoreTokenUseCase(
            tokenRepository = tokenRepository,
        )

        val result = useCase(token) as StoreTokenResult.Failure

        Truth.assertThat(result.e).isInstanceOf(IOException::class.java)
    }
}
