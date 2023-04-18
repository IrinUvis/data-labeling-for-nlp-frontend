@file:Suppress("MaxLineLength")

package it.nlp.frontend.domain.emotiontexts.usecase.impl

import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.mockk
import it.nlp.frontend.data.core.model.DataResult
import it.nlp.frontend.data.remote.emotion.assignments.model.dto.TextEmotionAssignmentInput
import it.nlp.frontend.data.remote.emotion.assignments.model.dto.TextEmotionAssignmentOutput
import it.nlp.frontend.data.remote.emotion.assignments.model.dto.EmotionDto
import it.nlp.frontend.data.remote.emotion.assignments.model.exception.TextEmotionAssignmentException
import it.nlp.frontend.data.remote.emotion.assignments.repository.TextEmotionAssignmentRepository
import it.nlp.frontend.data.remote.model.exception.NetworkException
import it.nlp.frontend.data.remote.model.exception.ServiceUnavailableException
import it.nlp.frontend.data.remote.model.exception.UnauthorizedException
import it.nlp.frontend.domain.emotiontexts.model.EmotionText
import it.nlp.frontend.domain.emotiontexts.model.Emotion
import it.nlp.frontend.domain.emotiontexts.model.SaveLabeledTextsResult
import it.nlp.frontend.domain.token.model.GetTokenFlowResult
import it.nlp.frontend.domain.token.model.Token
import it.nlp.frontend.domain.token.usecase.GetTokenFlowUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class ProdSaveLabeledTextsUseCaseTest {

    @Test
    fun useCase_invokedWithGetTokenFlowReturningTokenAndRepositoryReturningSuccess_returnsSaveLabeledTextsSuccess() =
        runTest {
            val numberOfTexts = 10
            val userId = "userId"
            val accessToken = "accessToken"
            val refreshToken = "refreshToken"
            val textId = "textId"
            val text = "text"
            val emotion = Emotion.Anger
            val emotionDto = EmotionDto.Anger
            val retrievedToken = Token(
                userId = userId,
                accessToken = accessToken,
                refreshToken = refreshToken,
            )
            val emotionTextToStore = EmotionText(
                id = textId,
                text = text,
                emotion = emotion
            )
            val textEmotionAssignmentInput = TextEmotionAssignmentInput(
                userId = userId,
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
            val getTokenFlowUseCaseMock: GetTokenFlowUseCase = mockk()

            coEvery { getTokenFlowUseCaseMock() } returns GetTokenFlowResult.Success(
                tokenFlow = flow {
                    emit(retrievedToken)
                }
            )

            coEvery {
                textEmotionAssignmentRepositoryMock.postTextEmotionAssignments(
                    textEmotionAssignmentInputs = textEmotionAssignmentInputs
                )
            } returns DataResult.Success(textEmotionAssignmentOutputs)

            val useCase = ProdSaveLabeledTextsUseCase(
                textEmotionAssignmentRepository = textEmotionAssignmentRepositoryMock,
                getTokenFlowUseCase = getTokenFlowUseCaseMock,
            )

            val result = useCase(textsToStore)

            val expectedResult = SaveLabeledTextsResult.Success

            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun useCase_invokedWithGetTokenFlowReturningFailure_returnsReadingTokenSaveLabeledTextsFailure() =
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
            val textsToStore = List(numberOfTexts) { emotionTextToStore }

            val textEmotionAssignmentRepositoryMock: TextEmotionAssignmentRepository = mockk()
            val getTokenFlowUseCaseMock: GetTokenFlowUseCase = mockk()

            coEvery { getTokenFlowUseCaseMock() } returns GetTokenFlowResult.Failure(IOException())

            val useCase = ProdSaveLabeledTextsUseCase(
                textEmotionAssignmentRepository = textEmotionAssignmentRepositoryMock,
                getTokenFlowUseCase = getTokenFlowUseCaseMock,
            )

            val result = useCase(textsToStore)

            val expectedResult = SaveLabeledTextsResult.Failure.ReadingToken

            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun useCase_invokedWithGetTokenFlowReturningNull_returnsNoTokenSaveLabeledTextsFailure() =
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
            val textsToStore = List(numberOfTexts) { emotionTextToStore }

            val textEmotionAssignmentRepositoryMock: TextEmotionAssignmentRepository = mockk()
            val getTokenFlowUseCaseMock: GetTokenFlowUseCase = mockk()

            coEvery { getTokenFlowUseCaseMock() } returns GetTokenFlowResult.Success(
                tokenFlow = flow { emit(null) }
            )

            val useCase = ProdSaveLabeledTextsUseCase(
                textEmotionAssignmentRepository = textEmotionAssignmentRepositoryMock,
                getTokenFlowUseCase = getTokenFlowUseCaseMock,
            )

            val result = useCase(textsToStore)

            val expectedResult = SaveLabeledTextsResult.Failure.NoToken

            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun useCase_invokedWithGetTokenFlowReturningTokenAndRepositoryReturningServiceUnavailableException_returnsServiceUnavailableSaveLabeledTextsFailure() =
        runTest {
            val numberOfTexts = 10
            val userId = "userId"
            val accessToken = "accessToken"
            val refreshToken = "refreshToken"
            val textId = "textId"
            val text = "text"
            val emotion = Emotion.Anger
            val retrievedToken = Token(
                userId = userId,
                accessToken = accessToken,
                refreshToken = refreshToken,
            )
            val emotionTextToStore = EmotionText(
                id = textId,
                text = text,
                emotion = emotion
            )
            val textEmotionAssignmentInput = TextEmotionAssignmentInput(
                userId = userId,
                textId = textId,
                emotion = emotion.toUppercaseString()
            )
            val textsToStore = List(numberOfTexts) { emotionTextToStore }
            val textEmotionAssignmentInputs =
                List(numberOfTexts) { textEmotionAssignmentInput }

            val textEmotionAssignmentRepositoryMock: TextEmotionAssignmentRepository = mockk()
            val getTokenFlowUseCaseMock: GetTokenFlowUseCase = mockk()

            coEvery { getTokenFlowUseCaseMock() } returns GetTokenFlowResult.Success(
                tokenFlow = flow {
                    emit(retrievedToken)
                }
            )

            coEvery {
                textEmotionAssignmentRepositoryMock.postTextEmotionAssignments(
                    textEmotionAssignmentInputs = textEmotionAssignmentInputs
                )
            } returns DataResult.Failure(ServiceUnavailableException(null, null))

            val useCase = ProdSaveLabeledTextsUseCase(
                textEmotionAssignmentRepository = textEmotionAssignmentRepositoryMock,
                getTokenFlowUseCase = getTokenFlowUseCaseMock,
            )

            val result = useCase(textsToStore)

            val expectedResult = SaveLabeledTextsResult.Failure.ServiceUnavailable

            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun useCase_invokedWithGetTokenFlowReturningTokenAndRepositoryReturningNetworkException_returnsNetworkSaveLabeledTextsFailure() =
        runTest {
            val numberOfTexts = 10
            val userId = "userId"
            val accessToken = "accessToken"
            val refreshToken = "refreshToken"
            val textId = "textId"
            val text = "text"
            val emotion = Emotion.Anger
            val retrievedToken = Token(
                userId = userId,
                accessToken = accessToken,
                refreshToken = refreshToken,
            )
            val emotionTextToStore = EmotionText(
                id = textId,
                text = text,
                emotion = emotion
            )
            val textEmotionAssignmentInput = TextEmotionAssignmentInput(
                userId = userId,
                textId = textId,
                emotion = emotion.toUppercaseString()
            )
            val textsToStore = List(numberOfTexts) { emotionTextToStore }
            val textEmotionAssignmentInputs =
                List(numberOfTexts) { textEmotionAssignmentInput }

            val textEmotionAssignmentRepositoryMock: TextEmotionAssignmentRepository = mockk()
            val getTokenFlowUseCaseMock: GetTokenFlowUseCase = mockk()

            coEvery { getTokenFlowUseCaseMock() } returns GetTokenFlowResult.Success(
                tokenFlow = flow {
                    emit(retrievedToken)
                }
            )

            coEvery {
                textEmotionAssignmentRepositoryMock.postTextEmotionAssignments(
                    textEmotionAssignmentInputs = textEmotionAssignmentInputs
                )
            } returns DataResult.Failure(NetworkException(null, null))

            val useCase = ProdSaveLabeledTextsUseCase(
                textEmotionAssignmentRepository = textEmotionAssignmentRepositoryMock,
                getTokenFlowUseCase = getTokenFlowUseCaseMock,
            )

            val result = useCase(textsToStore)

            val expectedResult = SaveLabeledTextsResult.Failure.Network

            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun useCase_invokedWithGetTokenFlowReturningTokenAndRepositoryReturningUnauthorizedException_returnsUnauthorizedSaveLabeledTextsFailure() =
        runTest {
            val numberOfTexts = 10
            val userId = "userId"
            val accessToken = "accessToken"
            val refreshToken = "refreshToken"
            val textId = "textId"
            val text = "text"
            val emotion = Emotion.Anger
            val retrievedToken = Token(
                userId = userId,
                accessToken = accessToken,
                refreshToken = refreshToken,
            )
            val emotionTextToStore = EmotionText(
                id = textId,
                text = text,
                emotion = emotion
            )
            val textEmotionAssignmentInput = TextEmotionAssignmentInput(
                userId = userId,
                textId = textId,
                emotion = emotion.toUppercaseString()
            )
            val textsToStore = List(numberOfTexts) { emotionTextToStore }
            val textEmotionAssignmentInputs =
                List(numberOfTexts) { textEmotionAssignmentInput }

            val textEmotionAssignmentRepositoryMock: TextEmotionAssignmentRepository = mockk()
            val getTokenFlowUseCaseMock: GetTokenFlowUseCase = mockk()

            coEvery { getTokenFlowUseCaseMock() } returns GetTokenFlowResult.Success(
                tokenFlow = flow {
                    emit(retrievedToken)
                }
            )

            coEvery {
                textEmotionAssignmentRepositoryMock.postTextEmotionAssignments(
                    textEmotionAssignmentInputs = textEmotionAssignmentInputs
                )
            } returns DataResult.Failure(UnauthorizedException(null, null))

            val useCase = ProdSaveLabeledTextsUseCase(
                textEmotionAssignmentRepository = textEmotionAssignmentRepositoryMock,
                getTokenFlowUseCase = getTokenFlowUseCaseMock,
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
            val userId = "userId"
            val accessToken = "accessToken"
            val refreshToken = "refreshToken"
            val textId = "textId"
            val text = "text"
            val emotion = Emotion.Anger
            val retrievedToken = Token(
                userId = userId,
                accessToken = accessToken,
                refreshToken = refreshToken,
            )
            val emotionTextToStore = EmotionText(
                id = textId,
                text = text,
                emotion = emotion
            )
            val textEmotionAssignmentInput = TextEmotionAssignmentInput(
                userId = userId,
                textId = textId,
                emotion = emotion.toUppercaseString()
            )
            val textsToStore = List(numberOfTexts) { emotionTextToStore }
            val textEmotionAssignmentInputs =
                List(numberOfTexts) { textEmotionAssignmentInput }

            val textEmotionAssignmentRepositoryMock: TextEmotionAssignmentRepository = mockk()
            val getTokenFlowUseCaseMock: GetTokenFlowUseCase = mockk()

            coEvery { getTokenFlowUseCaseMock() } returns GetTokenFlowResult.Success(
                tokenFlow = flow {
                    emit(retrievedToken)
                }
            )

            coEvery {
                textEmotionAssignmentRepositoryMock.postTextEmotionAssignments(
                    textEmotionAssignmentInputs = textEmotionAssignmentInputs
                )
            } returns DataResult.Failure(TextEmotionAssignmentException.WrongEmotion(errorMessage))

            val useCase = ProdSaveLabeledTextsUseCase(
                textEmotionAssignmentRepository = textEmotionAssignmentRepositoryMock,
                getTokenFlowUseCase = getTokenFlowUseCaseMock,
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
            val userId = "userId"
            val accessToken = "accessToken"
            val refreshToken = "refreshToken"
            val textId = "textId"
            val text = "text"
            val emotion = Emotion.Anger
            val retrievedToken = Token(
                userId = userId,
                accessToken = accessToken,
                refreshToken = refreshToken,
            )
            val emotionTextToStore = EmotionText(
                id = textId,
                text = text,
                emotion = emotion
            )
            val textEmotionAssignmentInput = TextEmotionAssignmentInput(
                userId = userId,
                textId = textId,
                emotion = emotion.toUppercaseString()
            )
            val textsToStore = List(numberOfTexts) { emotionTextToStore }
            val textEmotionAssignmentInputs =
                List(numberOfTexts) { textEmotionAssignmentInput }

            val textEmotionAssignmentRepositoryMock: TextEmotionAssignmentRepository = mockk()
            val getTokenFlowUseCaseMock: GetTokenFlowUseCase = mockk()

            coEvery { getTokenFlowUseCaseMock() } returns GetTokenFlowResult.Success(
                tokenFlow = flow {
                    emit(retrievedToken)
                }
            )

            coEvery {
                textEmotionAssignmentRepositoryMock.postTextEmotionAssignments(
                    textEmotionAssignmentInputs = textEmotionAssignmentInputs
                )
            } returns DataResult.Failure(
                TextEmotionAssignmentException.AssignmentAlreadyExists(
                errorMessage))

            val useCase = ProdSaveLabeledTextsUseCase(
                textEmotionAssignmentRepository = textEmotionAssignmentRepositoryMock,
                getTokenFlowUseCase = getTokenFlowUseCaseMock,
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

            val textEmotionAssignmentRepositoryMock: TextEmotionAssignmentRepository = mockk()
            val getTokenFlowUseCaseMock: GetTokenFlowUseCase = mockk()

            val useCase = ProdSaveLabeledTextsUseCase(
                textEmotionAssignmentRepository = textEmotionAssignmentRepositoryMock,
                getTokenFlowUseCase = getTokenFlowUseCaseMock,
            )

            val result = useCase(textsToStore) as SaveLabeledTextsResult.Failure.NonLabeledTexts

            Truth.assertThat(result.e).isInstanceOf(IllegalArgumentException::class.java)
        }

    @Test
    fun useCase_invokedWithGetTokenFlowReturningTokenAndRepositoryReturningUnknownException_returnsUnknownSaveLabeledTextsFailure() =
        runTest {
            val errorMessage = "errorMessage"
            val numberOfTexts = 10
            val userId = "userId"
            val accessToken = "accessToken"
            val refreshToken = "refreshToken"
            val textId = "textId"
            val text = "text"
            val emotion = Emotion.Anger
            val retrievedToken = Token(
                userId = userId,
                accessToken = accessToken,
                refreshToken = refreshToken,
            )
            val emotionTextToStore = EmotionText(
                id = textId,
                text = text,
                emotion = emotion
            )
            val textEmotionAssignmentInput = TextEmotionAssignmentInput(
                userId = userId,
                textId = textId,
                emotion = emotion.toUppercaseString()
            )
            val textsToStore = List(numberOfTexts) { emotionTextToStore }
            val textEmotionAssignmentInputs =
                List(numberOfTexts) { textEmotionAssignmentInput }

            val textEmotionAssignmentRepositoryMock: TextEmotionAssignmentRepository = mockk()
            val getTokenFlowUseCaseMock: GetTokenFlowUseCase = mockk()

            coEvery { getTokenFlowUseCaseMock() } returns GetTokenFlowResult.Success(
                tokenFlow = flow {
                    emit(retrievedToken)
                }
            )

            coEvery {
                textEmotionAssignmentRepositoryMock.postTextEmotionAssignments(
                    textEmotionAssignmentInputs = textEmotionAssignmentInputs
                )
            } returns DataResult.Failure(TextEmotionAssignmentException.Unknown(errorMessage))

            val useCase = ProdSaveLabeledTextsUseCase(
                textEmotionAssignmentRepository = textEmotionAssignmentRepositoryMock,
                getTokenFlowUseCase = getTokenFlowUseCaseMock,
            )

            val result = useCase(textsToStore)

            val expectedResult = SaveLabeledTextsResult.Failure.Unknown

            Truth.assertThat(result).isEqualTo(expectedResult)
        }
}
