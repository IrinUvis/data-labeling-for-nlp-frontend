@file:Suppress("MaxLineLength")

package it.nlp.frontend.domain.emotiontexts.usecase.impl

import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.mockk
import it.nlp.frontend.data.remote.emotion.texts.model.dto.EmotionTextOutput
import it.nlp.frontend.data.remote.emotion.texts.repository.EmotionTextRepository
import it.nlp.frontend.data.remote.model.ApiResponse
import it.nlp.frontend.data.remote.model.HttpStatus
import it.nlp.frontend.data.remote.model.exception.messages.TextExceptionMessage
import it.nlp.frontend.domain.emotiontexts.model.EmotionText
import it.nlp.frontend.domain.emotiontexts.model.GetTextsToLabelResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.net.ConnectException

@OptIn(ExperimentalCoroutinesApi::class)
class ProdGetTextsToLabelUseCaseTest {

    @Test
    fun useCase_invokedWithGetTokenFlowReturningTokenAndRepositoryReturningSuccess_returnsGetTextsToLabelSuccess() =
        runTest {
            val textsNumber = 10
            val textId = "textId"
            val textContent = "content"
            val emotionTextOutput = EmotionTextOutput(
                emotionTextId = textId,
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
                emotionTextRepositoryMock.getTextsForAssignment(textsNumber)
            } returns ApiResponse.Success(emotionTextOutputList)

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
                emotionTextRepositoryMock.getTextsForAssignment(textsNumber)
            } returns ApiResponse.Success(textsOutputList)

            val useCase = ProdGetTextsToLabelUseCase(
                emotionTextRepository = emotionTextRepositoryMock,
            )

            val result = useCase(textsNumber)

            val expectedResult = GetTextsToLabelResult.Failure.NoTexts

            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun useCase_invokedWithGetTokenReturningTokenAndRepositoryReturningUnauthorizedFailure_returnUnauthorizedUserGetTextsToLabelResult() =
        runTest {
            val textsNumber = 10

            val emotionTextRepositoryMock: EmotionTextRepository = mockk()

            coEvery {
                emotionTextRepositoryMock.getTextsForAssignment(textsNumber)
            } returns ApiResponse.Failure("", HttpStatus.Unauthorized.code)

            val useCase = ProdGetTextsToLabelUseCase(
                emotionTextRepository = emotionTextRepositoryMock,
            )

            val result = useCase(textsNumber)

            val expectedResult = GetTextsToLabelResult.Failure.UnauthorizedUser

            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun useCase_invokedWithGetTokenReturningTokenAndRepositoryReturningConnectException_returnNetworkGetTextsToLabelResult() =
        runTest {
            val textsNumber = 10

            val emotionTextRepositoryMock: EmotionTextRepository = mockk()

            coEvery {
                emotionTextRepositoryMock.getTextsForAssignment(textsNumber)
            } returns ApiResponse.Exception(ConnectException())

            val useCase = ProdGetTextsToLabelUseCase(
                emotionTextRepository = emotionTextRepositoryMock,
            )

            val result = useCase(textsNumber)

            val expectedResult = GetTextsToLabelResult.Failure.Network

            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun useCase_invokedWithGetTokenReturningTokenAndRepositoryReturningServiceUnavailableFailure_returnServiceUnavailableGetTextsToLabelResult() =
        runTest {
            val textsNumber = 10

            val emotionTextRepositoryMock: EmotionTextRepository = mockk()

            coEvery {
                emotionTextRepositoryMock.getTextsForAssignment(textsNumber)
            } returns ApiResponse.Failure("", HttpStatus.ServiceUnavailable.code)

            val useCase = ProdGetTextsToLabelUseCase(
                emotionTextRepository = emotionTextRepositoryMock,
            )

            val result = useCase(textsNumber)

            val expectedResult = GetTextsToLabelResult.Failure.ServiceUnavailable

            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun useCase_invokedWithGetTokenReturningTokenAndRepositoryReturningTextsNumberOutOfRangeFailure_returnUnexpectedGetTextsToLabelResult() =
        runTest {
            val textsNumber = 10

            val emotionTextRepositoryMock: EmotionTextRepository = mockk()

            coEvery {
                emotionTextRepositoryMock.getTextsForAssignment(textsNumber)
            } returns ApiResponse.Failure(
                TextExceptionMessage.TextsNumberOutOfRange.message,
                HttpStatus.BadRequest.code
            )

            val useCase = ProdGetTextsToLabelUseCase(
                emotionTextRepository = emotionTextRepositoryMock,
            )

            val result = useCase(textsNumber)

            val expectedResult = GetTextsToLabelResult.Failure.Unexpected

            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun useCase_invokedWithGetTokenReturningTokenAndRepositoryReturningCannotCompareNullsFailure_returnUnexpectedGetTextsToLabelResult() =
        runTest {
            val textsNumber = 10

            val emotionTextRepositoryMock: EmotionTextRepository = mockk()

            coEvery {
                emotionTextRepositoryMock.getTextsForAssignment(textsNumber)
            } returns ApiResponse.Failure(
                TextExceptionMessage.CannotCompareNullText.message,
                HttpStatus.BadRequest.code
            )

            val useCase = ProdGetTextsToLabelUseCase(
                emotionTextRepository = emotionTextRepositoryMock,
            )

            val result = useCase(textsNumber)

            val expectedResult = GetTextsToLabelResult.Failure.Unexpected

            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun useCase_invokedWithGetTokenReturningTokenAndRepositoryReturningUnknownException_returnUnexpectedGetTextsToLabelResult() =
        runTest {
            val textsNumber = 10

            val emotionTextRepositoryMock: EmotionTextRepository = mockk()

            coEvery {
                emotionTextRepositoryMock.getTextsForAssignment(textsNumber)
            } returns ApiResponse.Failure("", HttpStatus.BadRequest.code)

            val useCase = ProdGetTextsToLabelUseCase(
                emotionTextRepository = emotionTextRepositoryMock,
            )

            val result = useCase(textsNumber)

            val expectedResult = GetTextsToLabelResult.Failure.Unexpected

            Truth.assertThat(result).isEqualTo(expectedResult)
        }
}
