@file:Suppress("MaxLineLength")

package it.nlp.frontend.domain.emotiontexts.usecase.impl

import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.mockk
import it.nlp.frontend.data.remote.emotion.assignments.model.dto.EmotionDto
import it.nlp.frontend.data.remote.emotion.assignments.model.dto.TextEmotionAssignmentInput
import it.nlp.frontend.data.remote.emotion.assignments.model.dto.TextEmotionAssignmentOutput
import it.nlp.frontend.data.remote.emotion.assignments.repository.TextEmotionAssignmentRepository
import it.nlp.frontend.data.remote.model.ApiResponse
import it.nlp.frontend.data.remote.model.HttpStatus
import it.nlp.frontend.data.remote.model.exception.messages.TextEmotionAssignmentExceptionMessage
import it.nlp.frontend.domain.emotiontexts.model.Emotion
import it.nlp.frontend.domain.emotiontexts.model.EmotionText
import it.nlp.frontend.domain.emotiontexts.model.SaveLabeledTextsResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.net.SocketTimeoutException

@OptIn(ExperimentalCoroutinesApi::class)
class ProdSaveLabeledTextsUseCaseTest {

    @Test
    fun useCase_invokedWithGetTokenFlowReturningTokenAndRepositoryReturningSuccess_returnsSaveLabeledTextsSuccess() =
        runTest {
            val numberOfTexts = 10
            val userId = "userId"
            val textId = "textId"
            val text = "text"
            val emotion = Emotion.Anger
            val emotionDto = EmotionDto.Anger
            val emotionTextToStore = EmotionText(
                id = textId,
                text = text,
                emotion = emotion
            )
            val textEmotionAssignmentInput = TextEmotionAssignmentInput(
                textId = textId,
                emotion = emotion.toUppercaseString()
            )
            val textEmotionAssignmentOutput = TextEmotionAssignmentOutput(
                userId = userId,
                textId = textId,
                emotionDto = emotionDto
            )
            val textsToStore = List(numberOfTexts) { emotionTextToStore }
            val textEmotionAssignmentInputs =
                List(numberOfTexts) { textEmotionAssignmentInput }
            val textEmotionAssignmentOutputs =
                List(numberOfTexts) { textEmotionAssignmentOutput }

            val textEmotionAssignmentRepositoryMock: TextEmotionAssignmentRepository = mockk()

            coEvery {
                textEmotionAssignmentRepositoryMock.postAssignments(textEmotionAssignmentInputs)
            } returns ApiResponse.Success(textEmotionAssignmentOutputs)

            val useCase = ProdSaveLabeledTextsUseCase(
                textEmotionAssignmentRepository = textEmotionAssignmentRepositoryMock,
            )

            val result = useCase(textsToStore)

            val expectedResult = SaveLabeledTextsResult.Success

            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun useCase_invokedWithGetTokenFlowReturningTokenAndRepositoryReturningServiceUnavailableFailure_returnsServiceUnavailableSaveLabeledTextsFailure() =
        runTest {
            val numberOfTexts = 10
            val textId = "textId"
            val text = "text"
            val emotion = Emotion.Anger

            val emotionTextToStore = EmotionText(
                id = textId,
                text = text,
                emotion = emotion
            )
            val textEmotionAssignmentInput = TextEmotionAssignmentInput(
                textId = textId,
                emotion = emotion.toUppercaseString()
            )
            val textsToStore = List(numberOfTexts) { emotionTextToStore }
            val textEmotionAssignmentInputs =
                List(numberOfTexts) { textEmotionAssignmentInput }

            val textEmotionAssignmentRepositoryMock: TextEmotionAssignmentRepository = mockk()

            coEvery {
                textEmotionAssignmentRepositoryMock.postAssignments(textEmotionAssignmentInputs)
            } returns ApiResponse.Failure("", HttpStatus.ServiceUnavailable.code)

            val useCase = ProdSaveLabeledTextsUseCase(
                textEmotionAssignmentRepository = textEmotionAssignmentRepositoryMock,
            )

            val result = useCase(textsToStore)

            val expectedResult = SaveLabeledTextsResult.Failure.ServiceUnavailable

            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun useCase_invokedWithGetTokenFlowReturningTokenAndRepositoryReturningSocketTimeoutException_returnsNetworkSaveLabeledTextsFailure() =
        runTest {
            val numberOfTexts = 10
            val textId = "textId"
            val text = "text"
            val emotion = Emotion.Anger
            val emotionTextToStore = EmotionText(
                id = textId,
                text = text,
                emotion = emotion
            )
            val textEmotionAssignmentInput = TextEmotionAssignmentInput(
                textId = textId,
                emotion = emotion.toUppercaseString()
            )
            val textsToStore = List(numberOfTexts) { emotionTextToStore }
            val textEmotionAssignmentInputs =
                List(numberOfTexts) { textEmotionAssignmentInput }

            val textEmotionAssignmentRepositoryMock: TextEmotionAssignmentRepository = mockk()

            coEvery {
                textEmotionAssignmentRepositoryMock.postAssignments(textEmotionAssignmentInputs)
            } returns ApiResponse.Exception(SocketTimeoutException())

            val useCase = ProdSaveLabeledTextsUseCase(
                textEmotionAssignmentRepository = textEmotionAssignmentRepositoryMock,
            )

            val result = useCase(textsToStore)

            val expectedResult = SaveLabeledTextsResult.Failure.Network

            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun useCase_invokedWithGetTokenFlowReturningTokenAndRepositoryReturningUnauthorizedFailure_returnsUnauthorizedSaveLabeledTextsFailure() =
        runTest {
            val numberOfTexts = 10
            val textId = "textId"
            val text = "text"
            val emotion = Emotion.Anger
            val emotionTextToStore = EmotionText(
                id = textId,
                text = text,
                emotion = emotion
            )
            val textEmotionAssignmentInput = TextEmotionAssignmentInput(
                textId = textId,
                emotion = emotion.toUppercaseString()
            )
            val textsToStore = List(numberOfTexts) { emotionTextToStore }
            val textEmotionAssignmentInputs =
                List(numberOfTexts) { textEmotionAssignmentInput }

            val textEmotionAssignmentRepositoryMock: TextEmotionAssignmentRepository = mockk()

            coEvery {
                textEmotionAssignmentRepositoryMock.postAssignments(textEmotionAssignmentInputs)
            } returns ApiResponse.Failure("", HttpStatus.Unauthorized.code)

            val useCase = ProdSaveLabeledTextsUseCase(
                textEmotionAssignmentRepository = textEmotionAssignmentRepositoryMock,
            )

            val result = useCase(textsToStore)

            val expectedResult = SaveLabeledTextsResult.Failure.UnauthorizedUser

            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun useCase_invokedWithGetTokenFlowReturningTokenAndRepositoryReturningWrongEmotionFailure_returnsUnexpectedSaveLabeledTextsFailure() =
        runTest {
            val numberOfTexts = 10
            val textId = "textId"
            val text = "text"
            val emotion = Emotion.Anger
            val emotionTextToStore = EmotionText(
                id = textId,
                text = text,
                emotion = emotion
            )
            val textEmotionAssignmentInput = TextEmotionAssignmentInput(
                textId = textId,
                emotion = emotion.toUppercaseString()
            )
            val textsToStore = List(numberOfTexts) { emotionTextToStore }
            val textEmotionAssignmentInputs =
                List(numberOfTexts) { textEmotionAssignmentInput }

            val textEmotionAssignmentRepositoryMock: TextEmotionAssignmentRepository = mockk()

            coEvery {
                textEmotionAssignmentRepositoryMock.postAssignments(textEmotionAssignmentInputs)
            } returns ApiResponse.Failure(
                TextEmotionAssignmentExceptionMessage.WrongEmotion.message,
                HttpStatus.BadRequest.code
            )

            val useCase = ProdSaveLabeledTextsUseCase(
                textEmotionAssignmentRepository = textEmotionAssignmentRepositoryMock,
            )

            val result = useCase(textsToStore)

            val expectedResult = SaveLabeledTextsResult.Failure.Unexpected

            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun useCase_invokedWithGetTokenFlowReturningTokenAndRepositoryReturningAssignmentAlreadyExistsFailure_returnsUnexpectedSaveLabeledTextsFailure() =
        runTest {
            val numberOfTexts = 10
            val textId = "textId"
            val text = "text"
            val emotion = Emotion.Anger
            val emotionTextToStore = EmotionText(
                id = textId,
                text = text,
                emotion = emotion
            )
            val textEmotionAssignmentInput = TextEmotionAssignmentInput(
                textId = textId,
                emotion = emotion.toUppercaseString()
            )
            val textsToStore = List(numberOfTexts) { emotionTextToStore }
            val textEmotionAssignmentInputs =
                List(numberOfTexts) { textEmotionAssignmentInput }

            val textEmotionAssignmentRepositoryMock: TextEmotionAssignmentRepository = mockk()

            coEvery {
                textEmotionAssignmentRepositoryMock.postAssignments(textEmotionAssignmentInputs)
            } returns ApiResponse.Failure(
                TextEmotionAssignmentExceptionMessage.AssignmentAlreadyExists.message,
                HttpStatus.BadRequest.code
            )

            val useCase = ProdSaveLabeledTextsUseCase(
                textEmotionAssignmentRepository = textEmotionAssignmentRepositoryMock,
            )

            val result = useCase(textsToStore)

            val expectedResult = SaveLabeledTextsResult.Failure.Unexpected

            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun useCase_invokedWithUnlabeledText_returnsNonLabeledTextsSaveLabeledTextsFailure() =
        runTest {
            val numberOfTexts = 10
            val textId = "textId"
            val text = "text"
            val emotionTextToStore = EmotionText(
                id = textId,
                text = text,
                emotion = null
            )
            val textsToStore = List(numberOfTexts) { emotionTextToStore }

            val textEmotionAssignmentRepositoryMock: TextEmotionAssignmentRepository = mockk()

            val useCase = ProdSaveLabeledTextsUseCase(
                textEmotionAssignmentRepository = textEmotionAssignmentRepositoryMock,
            )

            val result = useCase(textsToStore)

            val expectedResult = SaveLabeledTextsResult.Failure.Unexpected

            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun useCase_invokedWithGetTokenFlowReturningTokenAndRepositoryReturningUnknownFailure_returnsUnexpectedSaveLabeledTextsFailure() =
        runTest {
            val numberOfTexts = 10
            val textId = "textId"
            val text = "text"
            val emotion = Emotion.Anger
            val emotionTextToStore = EmotionText(
                id = textId,
                text = text,
                emotion = emotion
            )
            val textEmotionAssignmentInput = TextEmotionAssignmentInput(
                textId = textId,
                emotion = emotion.toUppercaseString()
            )
            val textsToStore = List(numberOfTexts) { emotionTextToStore }
            val textEmotionAssignmentInputs =
                List(numberOfTexts) { textEmotionAssignmentInput }

            val textEmotionAssignmentRepositoryMock: TextEmotionAssignmentRepository = mockk()

            coEvery {
                textEmotionAssignmentRepositoryMock.postAssignments(textEmotionAssignmentInputs)
            } returns ApiResponse.Failure("", HttpStatus.BadRequest.code)

            val useCase = ProdSaveLabeledTextsUseCase(
                textEmotionAssignmentRepository = textEmotionAssignmentRepositoryMock,
            )

            val result = useCase(textsToStore)

            val expectedResult = SaveLabeledTextsResult.Failure.Unexpected

            Truth.assertThat(result).isEqualTo(expectedResult)
        }
}
