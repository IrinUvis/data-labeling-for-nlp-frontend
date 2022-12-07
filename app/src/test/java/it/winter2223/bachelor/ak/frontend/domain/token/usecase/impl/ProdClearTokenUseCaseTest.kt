package it.winter2223.bachelor.ak.frontend.domain.token.usecase.impl

import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.mockk
import it.winter2223.bachelor.ak.frontend.data.local.token.repository.TokenRepository
import it.winter2223.bachelor.ak.frontend.domain.token.model.ClearTokenResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class ProdClearTokenUseCaseTest {

    @Test
    fun useCase_invokedWithRepositorySucceeding_returnsSuccess() = runTest {
        val tokenRepositoryMock: TokenRepository = mockk()

        coEvery { tokenRepositoryMock.clearToken() } returns Unit

        val useCase = ProdClearTokenUseCase(
            tokenRepository = tokenRepositoryMock,
        )

        val expectedResult = ClearTokenResult.Success

        val result = useCase()

        Truth.assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun useCase_invokedWithRepositoryThrowingIOException_returnsFailure() = runTest {
        val tokenRepositoryMock: TokenRepository = mockk()

        coEvery { tokenRepositoryMock.clearToken() } throws IOException()

        val useCase = ProdClearTokenUseCase(
            tokenRepository = tokenRepositoryMock,
        )

        val result = useCase() as ClearTokenResult.Failure

        Truth.assertThat(result.e).isInstanceOf(IOException::class.java)
    }
}
