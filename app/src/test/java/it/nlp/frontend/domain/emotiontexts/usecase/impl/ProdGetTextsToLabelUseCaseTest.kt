@file:Suppress("MaxLineLength")

package it.nlp.frontend.domain.emotiontexts.usecase.impl

import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.mockk
import it.nlp.frontend.data.core.model.DataResult
import it.nlp.frontend.data.remote.emotion.texts.model.dto.EmotionTextOutput
import it.nlp.frontend.data.remote.emotion.texts.model.exception.EmotionTextException
import it.nlp.frontend.data.remote.emotion.texts.repository.EmotionTextRepository
import it.nlp.frontend.data.remote.model.exception.NetworkException
import it.nlp.frontend.data.remote.model.exception.ServiceUnavailableException
import it.nlp.frontend.data.remote.model.exception.UnauthorizedException
import it.nlp.frontend.domain.emotiontexts.model.EmotionText
import it.nlp.frontend.domain.emotiontexts.model.GetTextsToLabelResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProdGetTextsToLabelUseCaseTest {

    @Test
    fun useCase_invokedWithGetTokenFlowReturningTokenAndRepositoryReturningSuccess_returnsGetTextsToLabelSuccess() =
        runTest {
            val textsNumber = 10
            val textId = "textId"
            val textContent = "content"
            val emotionTextOutput = EmotionTextOutput(
                id = textId,
                content = textContent
            )
            val emotionText = EmotionText(
                id = textId,
                text = textContent,
            )
            val emotionTextOutputList = List(textsNumber) { emotionTextOutput }
            val textsList = List(textsNumber) { emotionText }

            val emotionTextRepositoryMock: EmotionTextRepository = mockk()

            coEvery {
                emotionTextRepositoryMock.fetchEmotionTextsToBeAssigned(
                    emotionTextsNumber = textsNumber,
                )
            } returns DataResult.Success(emotionTextOutputList)

            val useCase = ProdGetTextsToLabelUseCase(
                emotionTextRepository = emotionTextRepositoryMock,
            )

            val result = useCase(textsNumber)

            val expectedResult = GetTextsToLabelResult.Success(textsList)

            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun useCase_invokedWithGetTokenReturningTokenAndRepositoryReturningEmptyList_returnNoTextsGetTextsToLabelResult() =
        runTest {
            val textsNumber = 10
            val textsOutputList = emptyList<EmotionTextOutput>()

            val emotionTextRepositoryMock: EmotionTextRepository = mockk()

            coEvery {
                emotionTextRepositoryMock.fetchEmotionTextsToBeAssigned(
                    emotionTextsNumber = textsNumber,
                )
            } returns DataResult.Success(textsOutputList)

            val useCase = ProdGetTextsToLabelUseCase(
                emotionTextRepository = emotionTextRepositoryMock,
            )

            val result = useCase(textsNumber)

            val expectedResult = GetTextsToLabelResult.Failure.NoTexts

            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun useCase_invokedWithGetTokenReturningTokenAndRepositoryReturningUnauthorizedException_returnUnauthorizedUserGetTextsToLabelResult() =
        runTest {
            val textsNumber = 10

            val emotionTextRepositoryMock: EmotionTextRepository = mockk()

            coEvery {
                emotionTextRepositoryMock.fetchEmotionTextsToBeAssigned(
                    emotionTextsNumber = textsNumber,
                )
            } returns DataResult.Failure(UnauthorizedException(null, null))

            val useCase = ProdGetTextsToLabelUseCase(
                emotionTextRepository = emotionTextRepositoryMock,
            )

            val result = useCase(textsNumber)

            val expectedResult = GetTextsToLabelResult.Failure.UnauthorizedUser

            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun useCase_invokedWithGetTokenReturningTokenAndRepositoryReturningNetworkException_returnNetworkGetTextsToLabelResult() =
        runTest {
            val textsNumber = 10

            val emotionTextRepositoryMock: EmotionTextRepository = mockk()

            coEvery {
                emotionTextRepositoryMock.fetchEmotionTextsToBeAssigned(
                    emotionTextsNumber = textsNumber,
                )
            } returns DataResult.Failure(NetworkException(null, null))

            val useCase = ProdGetTextsToLabelUseCase(
                emotionTextRepository = emotionTextRepositoryMock,
            )

            val result = useCase(textsNumber)

            val expectedResult = GetTextsToLabelResult.Failure.Network

            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun useCase_invokedWithGetTokenReturningTokenAndRepositoryReturningServiceUnavailableException_returnServiceUnavailableGetTextsToLabelResult() =
        runTest {
            val textsNumber = 10

            val emotionTextRepositoryMock: EmotionTextRepository = mockk()

            coEvery {
                emotionTextRepositoryMock.fetchEmotionTextsToBeAssigned(
                    emotionTextsNumber = textsNumber,
                )
            } returns DataResult.Failure(ServiceUnavailableException(null, null))

            val useCase = ProdGetTextsToLabelUseCase(
                emotionTextRepository = emotionTextRepositoryMock,
            )

            val result = useCase(textsNumber)

            val expectedResult = GetTextsToLabelResult.Failure.ServiceUnavailable

            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun useCase_invokedWithGetTokenReturningTokenAndRepositoryReturningTextsNumberOutOfRangeException_returnTextsNumberOutOfRangeGetTextsToLabelResult() =
        runTest {
            val textsNumber = 10
            val errorMessage = "errorMessage"

            val emotionTextRepositoryMock: EmotionTextRepository = mockk()

            coEvery {
                emotionTextRepositoryMock.fetchEmotionTextsToBeAssigned(
                    emotionTextsNumber = textsNumber,
                )
            } returns DataResult.Failure(EmotionTextException.NumberOutOfRange(errorMessage))

            val useCase = ProdGetTextsToLabelUseCase(
                emotionTextRepository = emotionTextRepositoryMock,
            )

            val result = useCase(textsNumber)

            val expectedResult = GetTextsToLabelResult.Failure.TextsNumberOutOfRange

            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun useCase_invokedWithGetTokenReturningTokenAndRepositoryReturningCannotCompareNullsException_returnUnknownGetTextsToLabelResult() =
        runTest {
            val textsNumber = 10
            val errorMessage = "errorMessage"

            val emotionTextRepositoryMock: EmotionTextRepository = mockk()

            coEvery {
                emotionTextRepositoryMock.fetchEmotionTextsToBeAssigned(
                    emotionTextsNumber = textsNumber,
                )
            } returns DataResult.Failure(EmotionTextException.CannotCompareNulls(errorMessage))

            val useCase = ProdGetTextsToLabelUseCase(
                emotionTextRepository = emotionTextRepositoryMock,
            )

            val result = useCase(textsNumber)

            val expectedResult = GetTextsToLabelResult.Failure.Unknown

            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun useCase_invokedWithGetTokenReturningTokenAndRepositoryReturningUnknownException_returnUnknownGetTextsToLabelResult() =
        runTest {
            val textsNumber = 10
            val errorMessage = "errorMessage"

            val emotionTextRepositoryMock: EmotionTextRepository = mockk()

            coEvery {
                emotionTextRepositoryMock.fetchEmotionTextsToBeAssigned(
                    emotionTextsNumber = textsNumber,
                )
            } returns DataResult.Failure(EmotionTextException.Unknown(errorMessage))

            val useCase = ProdGetTextsToLabelUseCase(
                emotionTextRepository = emotionTextRepositoryMock,
            )

            val result = useCase(textsNumber)

            val expectedResult = GetTextsToLabelResult.Failure.Unknown

            Truth.assertThat(result).isEqualTo(expectedResult)
        }
}
