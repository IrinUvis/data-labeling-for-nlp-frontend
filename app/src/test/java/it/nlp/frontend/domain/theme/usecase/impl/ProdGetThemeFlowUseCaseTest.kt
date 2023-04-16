package it.nlp.frontend.domain.theme.usecase.impl

import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.mockk
import it.winter2223.bachelor.ak.frontend.data.local.theme.model.ThemePreferences
import it.winter2223.bachelor.ak.frontend.data.local.theme.repository.ThemeRepository
import it.winter2223.bachelor.ak.frontend.domain.theme.model.GetThemeFlowResult
import it.winter2223.bachelor.ak.frontend.domain.theme.model.Theme
import it.winter2223.bachelor.ak.frontend.domain.theme.usecase.impl.ProdGetThemeFlowUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class ProdGetThemeFlowUseCaseTest {

    @Test
    fun useCase_invokedWithRepositoryReturningThemePreferences_returnsSuccess() = runTest {
        val themePreferences = ThemePreferences.System

        val themeRepositoryMock: ThemeRepository = mockk()

        coEvery { themeRepositoryMock.themeFlow() } returns flow { emit(themePreferences) }

        val useCase = ProdGetThemeFlowUseCase(
            themeRepository = themeRepositoryMock,
        )

        val expectedResult = Theme.System

        val resultFlow = useCase() as GetThemeFlowResult.Success

        Truth.assertThat(resultFlow.themeFlow.first()).isEqualTo(expectedResult)
    }

    @Test
    fun useCase_invokedWithRepositoryThrowingIOException_returnsSuccess() = runTest {
        val themeRepositoryMock: ThemeRepository = mockk()

        coEvery { themeRepositoryMock.themeFlow() } throws IOException()

        val useCase = ProdGetThemeFlowUseCase(
            themeRepository = themeRepositoryMock,
        )

        val result = useCase() as GetThemeFlowResult.Failure

        Truth.assertThat(result.e).isInstanceOf(IOException::class.java)
    }
}
