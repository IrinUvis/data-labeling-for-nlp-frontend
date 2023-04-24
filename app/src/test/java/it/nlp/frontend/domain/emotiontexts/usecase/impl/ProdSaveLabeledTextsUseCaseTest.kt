@file:Suppress("MaxLineLength")

package it.nlp.frontend.domain.emotiontexts.usecase.impl

import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.mockk
import it.nlp.frontend.data.remote.emotion.assignments.model.dto.EmotionDto
import it.nlp.frontend.data.remote.emotion.assignments.model.dto.TextEmotionAssignmentInput
import it.nlp.frontend.data.remote.emotion.assignments.model.dto.TextEmotionAssignmentOutput
import it.nlp.frontend.data.remote.emotion.assignments.model.exception.TextEmotionAssignmentException
import it.nlp.frontend.data.remote.emotion.assignments.repository.TextEmotionAssignmentService
import it.nlp.frontend.domain.emotiontexts.model.Emotion
import it.nlp.frontend.domain.emotiontexts.model.EmotionText
import it.nlp.frontend.domain.emotiontexts.model.SaveLabeledTextsResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

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

            val textEmotionAssignmentRepositoryMock: TextEmotionAssignmentService = mockk()

            coEvery {
                textEmotionAssignmentRepositoryMock.postTextEmotionAssignments(
                    textEmotionAssignmentInputs = textEmotionAssignmentInputs
                )
            } returns DataResult.Success(textEmotionAssignmentOutputs)

            val useCase = ProdSaveLabeledTextsUseCase(
                textEmotionAssignmentRepository = textEmotionAssignmentRepositoryMock,
            )

            val result = useCase(textsToStore)

            val expectedResult = SaveLabeledTextsResult.Success

            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun useCase_invokedWithGetTokenFlowReturningTokenAndRepositoryReturningServiceUnavailableException_returnsServiceUnavailableSaveLabeledTextsFailure() =
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

            val textEmotionAssignmentRepositoryMock: TextEmotionAssignmentService = mockk()

            coEvery {
                textEmotionAssignmentRepositoryMock.postTextEmotionAssignments(
                    textEmotionAssignmentInputs = textEmotionAssignmentInputs
                )
            } returns DataResult.Failure(ServiceUnavailableException(null, null))

            val useCase = ProdSaveLabeledTextsUseCase(
                textEmotionAssignmentRepository = textEmotionAssignmentRepositoryMock,
            )

            val result = useCase(textsToStore)

            val expectedResult = SaveLabeledTextsResult.Failure.ServiceUnavailable

            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun useCase_invokedWithGetTokenFlowReturningTokenAndRepositoryReturningNetworkException_returnsNetworkSaveLabeledTextsFailure() =
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

            val textEmotionAssignmentRepositoryMock: TextEmotionAssignmentService = mockk()

            coEvery {
                textEmotionAssignmentRepositoryMock.postTextEmotionAssignments(
                    textEmotionAssignmentInputs = textEmotionAssignmentInputs
                )
            } returns DataResult.Failure(NetworkException(null, null))

            val useCase = ProdSaveLabeledTextsUseCase(
                textEmotionAssignmentRepository = textEmotionAssignmentRepositoryMock,
            )

            val result = useCase(textsToStore)

            val expectedResult = SaveLabeledTextsResult.Failure.Network

            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun useCase_invokedWithGetTokenFlowReturningTokenAndRepositoryReturningUnauthorizedException_returnsUnauthorizedSaveLabeledTextsFailure() =
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

            val textEmotionAssignmentRepositoryMock: TextEmotionAssignmentService = mockk()

            coEvery {
                textEmotionAssignmentRepositoryMock.postTextEmotionAssignments(
                    textEmotionAssignmentInputs = textEmotionAssignmentInputs
                )
            } returns DataResult.Failure(UnauthorizedException(null, null))

            val useCase = ProdSaveLabeledTextsUseCase(
                textEmotionAssignmentRepository = textEmotionAssignmentRepositoryMock,
            )

            val result = useCase(textsToStore)

            val expectedResult = SaveLabeledTextsResult.Failure.UnauthorizedUser

            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun useCase_invokedWithGetTokenFlowReturningTokenAndRepositoryReturningWrongEmotionException_returnsWrongEmotionSaveLabeledTextsFailure() =
        runTest {
            val errorMessage = "errorMessage"
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

            val textEmotionAssignmentRepositoryMock: TextEmotionAssignmentService = mockk()

            coEvery {
                textEmotionAssignmentRepositoryMock.postTextEmotionAssignments(
                    textEmotionAssignmentInputs = textEmotionAssignmentInputs
                )
            } returns DataResult.Failure(TextEmotionAssignmentException.WrongEmotion(errorMessage))

            val useCase = ProdSaveLabeledTextsUseCase(
                textEmotionAssignmentRepository = textEmotionAssignmentRepositoryMock,
            )

            val result = useCase(textsToStore)

            val expectedResult = SaveLabeledTextsResult.Failure.WrongEmotionParsing

            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun useCase_invokedWithGetTokenFlowReturningTokenAndRepositoryReturningAssignmentAlreadyExistsException_returnsAlreadyAssignedByThisUserSaveLabeledTextsFailure() =
        runTest {
            val errorMessage = "errorMessage"
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

            val textEmotionAssignmentRepositoryMock: TextEmotionAssignmentService = mockk()

            coEvery {
                textEmotionAssignmentRepositoryMock.postTextEmotionAssignments(
                    textEmotionAssignmentInputs = textEmotionAssignmentInputs
                )
            } returns DataResult.Failure(
                TextEmotionAssignmentException.AssignmentAlreadyExists(
                errorMessage))

            val useCase = ProdSaveLabeledTextsUseCase(
                textEmotionAssignmentRepository = textEmotionAssignmentRepositoryMock,
            )

            val result = useCase(textsToStore)

            val expectedResult = SaveLabeledTextsResult.Failure.AlreadyAssignedByThisUser

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

            val textEmotionAssignmentRepositoryMock: TextEmotionAssignmentService = mockk()

            val useCase = ProdSaveLabeledTextsUseCase(
                textEmotionAssignmentRepository = textEmotionAssignmentRepositoryMock,
            )

            val result = useCase(textsToStore) as SaveLabeledTextsResult.Failure.NonLabeledTexts

            Truth.assertThat(result.e).isInstanceOf(IllegalArgumentException::class.java)
        }

    @Test
    fun useCase_invokedWithGetTokenFlowReturningTokenAndRepositoryReturningUnknownException_returnsUnknownSaveLabeledTextsFailure() =
        runTest {
            val errorMessage = "errorMessage"
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

            val textEmotionAssignmentRepositoryMock: TextEmotionAssignmentService = mockk()

            coEvery {
                textEmotionAssignmentRepositoryMock.postTextEmotionAssignments(
                    textEmotionAssignmentInputs = textEmotionAssignmentInputs
                )
            } returns DataResult.Failure(TextEmotionAssignmentException.Unknown(errorMessage))

            val useCase = ProdSaveLabeledTextsUseCase(
                textEmotionAssignmentRepository = textEmotionAssignmentRepositoryMock,
            )

            val result = useCase(textsToStore)

            val expectedResult = SaveLabeledTextsResult.Failure.Unknown

            Truth.assertThat(result).isEqualTo(expectedResult)
        }
}
