package it.nlp.frontend.domain.token.usecase.impl

import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.mockk
import it.nlp.frontend.data.local.token.model.TokenPreferences
import it.nlp.frontend.data.local.token.repository.TokenRepository
import it.nlp.frontend.domain.token.model.GetTokenFlowResult
import it.nlp.frontend.domain.token.model.Token
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class ProdGetTokenFlowUseCaseTest {

    @Test
    fun useCase_invokedWithRepositoryReturningToken_returnsSuccessWithToken() = runTest {
        val authToken = "authToken"
        val refreshToken = "refreshToken"
        val userId = "userId"
        val tokenPreferences = TokenPreferences(
            authToken = authToken,
            refreshToken = refreshToken,
            userId = userId,
        )

        val tokenRepositoryMock: TokenRepository = mockk()

        coEvery { tokenRepositoryMock.tokenFlow() } returns flow { emit(tokenPreferences) }

        val useCase = ProdGetTokenFlowUseCase(
            tokenRepository = tokenRepositoryMock,
        )

        val expectedToken = Token(
            authToken = authToken,
            refreshToken = refreshToken,
            userId = userId,
        )

        val result = useCase() as GetTokenFlowResult.Success

        Truth.assertThat(result.tokenFlow.first()).isEqualTo(expectedToken)
    }

    @Test
    fun useCase_invokedWithRepositoryReturningNullToken_returnsSuccessWithNullToken() = runTest {
        val tokenRepositoryMock: TokenRepository = mockk()

        coEvery { tokenRepositoryMock.tokenFlow() } returns flow { emit(null) }

        val useCase = ProdGetTokenFlowUseCase(
            tokenRepository = tokenRepositoryMock,
        )

        val result = useCase() as GetTokenFlowResult.Success

        Truth.assertThat(result.tokenFlow.first()).isNull()
    }

    @Test
    fun useCase_invokedWithRepositoryThrowingIOException_returnsFailure() = runTest {
        val tokenRepositoryMock: TokenRepository = mockk()

        coEvery { tokenRepositoryMock.tokenFlow() } throws IOException()

        val useCase = ProdGetTokenFlowUseCase(
            tokenRepository = tokenRepositoryMock,
        )

        val result = useCase() as GetTokenFlowResult.Failure

        Truth.assertThat(result.e).isInstanceOf(IOException::class.java)
    }
}
