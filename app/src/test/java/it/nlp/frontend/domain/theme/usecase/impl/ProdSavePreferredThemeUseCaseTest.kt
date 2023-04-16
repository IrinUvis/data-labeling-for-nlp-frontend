package it.nlp.frontend.domain.theme.usecase.impl

import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.mockk
import it.winter2223.bachelor.ak.frontend.data.local.theme.model.ThemePreferences
import it.winter2223.bachelor.ak.frontend.data.local.theme.repository.ThemeRepository
import it.winter2223.bachelor.ak.frontend.domain.theme.model.SavePreferredThemeResult
import it.winter2223.bachelor.ak.frontend.domain.theme.model.Theme
import it.winter2223.bachelor.ak.frontend.domain.theme.usecase.impl.ProdSavePreferredThemeUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class ProdSavePreferredThemeUseCaseTest {

    @Test
    fun useCase_invokedWithRepositorySucceeding_returnsSuccess() = runTest {
        val theme = Theme.Dark
        val themePreferences = ThemePreferences.valueOf(theme.name)

        val themeRepositoryMock: ThemeRepository = mockk()

        coEvery { themeRepositoryMock.storeTheme(themePreferences) } returns Unit

        val useCase = ProdSavePreferredThemeUseCase(
            themeRepository = themeRepositoryMock,
        )

        val expectedResult = SavePreferredThemeResult.Success

        val result = useCase(theme)

        Truth.assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun useCase_invokedWithRepositoryThrowingIOException_returnsFailure() = runTest {
        val theme = Theme.Dark
        val themePreferences = ThemePreferences.valueOf(theme.name)

        val themeRepositoryMock: ThemeRepository = mockk()

        coEvery { themeRepositoryMock.storeTheme(themePreferences) } throws IOException()

        val useCase = ProdSavePreferredThemeUseCase(
            themeRepository = themeRepositoryMock,
        )

        val result = useCase(theme) as SavePreferredThemeResult.Failure

        Truth.assertThat(result.e).isInstanceOf(IOException::class.java)
    }
}
