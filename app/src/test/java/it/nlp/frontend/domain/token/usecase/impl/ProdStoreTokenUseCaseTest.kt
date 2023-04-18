package it.nlp.frontend.domain.token.usecase.impl

import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.mockk
import it.nlp.frontend.data.local.token.model.TokenPreferences
import it.nlp.frontend.data.local.token.repository.TokenRepository
import it.nlp.frontend.domain.token.model.StoreTokenResult
import it.nlp.frontend.domain.token.model.Token
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
            accessToken = authToken,
            refreshToken = refreshToken,
            userId = userId,
        )
        val token = Token(
            accessToken = authToken,
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
            accessToken = authToken,
            refreshToken = refreshToken,
            userId = userId,
        )
        val token = Token(
            accessToken = authToken,
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
