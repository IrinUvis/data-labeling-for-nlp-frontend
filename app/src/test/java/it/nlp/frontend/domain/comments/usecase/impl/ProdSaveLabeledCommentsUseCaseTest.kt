@file:Suppress("MaxLineLength")

package it.nlp.frontend.domain.comments.usecase.impl

import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.mockk
import it.nlp.frontend.data.core.model.DataResult
import it.nlp.frontend.data.remote.emotion.assignments.model.dto.CommentEmotionAssignmentInput
import it.nlp.frontend.data.remote.emotion.assignments.model.dto.CommentEmotionAssignmentOutput
import it.nlp.frontend.data.remote.emotion.assignments.model.dto.EmotionDto
import it.nlp.frontend.data.remote.emotion.assignments.model.exception.CommentEmotionAssignmentException
import it.nlp.frontend.data.remote.emotion.assignments.repository.CommentEmotionAssignmentRepository
import it.nlp.frontend.data.remote.model.exception.NetworkException
import it.nlp.frontend.data.remote.model.exception.ServiceUnavailableException
import it.nlp.frontend.data.remote.model.exception.UnauthorizedException
import it.nlp.frontend.domain.comments.model.Comment
import it.nlp.frontend.domain.comments.model.Emotion
import it.nlp.frontend.domain.comments.model.SaveLabeledCommentsResult
import it.nlp.frontend.domain.token.model.GetTokenFlowResult
import it.nlp.frontend.domain.token.model.Token
import it.nlp.frontend.domain.token.usecase.GetTokenFlowUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class ProdSaveLabeledCommentsUseCaseTest {

    @Test
    fun useCase_invokedWithGetTokenFlowReturningTokenAndRepositoryReturningSuccess_returnsSaveLabeledCommentsSuccess() =
        runTest {
            val numberOfComments = 10
            val userId = "userId"
            val authToken = "authToken"
            val refreshToken = "refreshToken"
            val id = "id"
            val text = "text"
            val emotion = Emotion.Anger
            val emotionDto = EmotionDto.Anger
            val assignmentId = "assignmentId"
            val retrievedToken = Token(
                userId = userId,
                accessToken = authToken,
                refreshToken = refreshToken,
            )
            val commentToStore = Comment(
                id = id,
                text = text,
                emotion = emotion
            )
            val commentEmotionAssignmentInput = CommentEmotionAssignmentInput(
                userId = userId,
                commentId = id,
                emotion = emotion.toUppercaseString()
            )
            val commentEmotionAssignmentOutput = CommentEmotionAssignmentOutput(
                assignmentId = assignmentId,
                userId = userId,
                commentId = id,
                emotionDto = emotionDto
            )
            val commentsToStore = List(numberOfComments) { commentToStore }
            val commentEmotionAssignmentInputs =
                List(numberOfComments) { commentEmotionAssignmentInput }
            val commentEmotionAssignmentOutputs =
                List(numberOfComments) { commentEmotionAssignmentOutput }

            val commentEmotionAssignmentRepositoryMock: CommentEmotionAssignmentRepository = mockk()
            val getTokenFlowUseCaseMock: GetTokenFlowUseCase = mockk()

            coEvery { getTokenFlowUseCaseMock() } returns GetTokenFlowResult.Success(
                tokenFlow = flow {
                    emit(retrievedToken)
                }
            )

            coEvery {
                commentEmotionAssignmentRepositoryMock.postCommentEmotionAssignments(
                    commentEmotionAssignmentInputs = commentEmotionAssignmentInputs
                )
            } returns DataResult.Success(commentEmotionAssignmentOutputs)

            val useCase = ProdSaveLabeledCommentsUseCase(
                commentEmotionAssignmentRepository = commentEmotionAssignmentRepositoryMock,
                getTokenFlowUseCase = getTokenFlowUseCaseMock,
            )

            val result = useCase(commentsToStore)

            val expectedResult = SaveLabeledCommentsResult.Success

            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun useCase_invokedWithGetTokenFlowReturningFailure_returnsReadingTokenSaveLabeledCommentsFailure() =
        runTest {
            val numberOfComments = 10
            val id = "id"
            val text = "text"
            val emotion = Emotion.Anger
            val commentToStore = Comment(
                id = id,
                text = text,
                emotion = emotion
            )
            val commentsToStore = List(numberOfComments) { commentToStore }

            val commentEmotionAssignmentRepositoryMock: CommentEmotionAssignmentRepository = mockk()
            val getTokenFlowUseCaseMock: GetTokenFlowUseCase = mockk()

            coEvery { getTokenFlowUseCaseMock() } returns GetTokenFlowResult.Failure(IOException())

            val useCase = ProdSaveLabeledCommentsUseCase(
                commentEmotionAssignmentRepository = commentEmotionAssignmentRepositoryMock,
                getTokenFlowUseCase = getTokenFlowUseCaseMock,
            )

            val result = useCase(commentsToStore)

            val expectedResult = SaveLabeledCommentsResult.Failure.ReadingToken

            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun useCase_invokedWithGetTokenFlowReturningNull_returnsNoTokenSaveLabeledCommentsFailure() =
        runTest {
            val numberOfComments = 10
            val id = "id"
            val text = "text"
            val emotion = Emotion.Anger
            val commentToStore = Comment(
                id = id,
                text = text,
                emotion = emotion
            )
            val commentsToStore = List(numberOfComments) { commentToStore }

            val commentEmotionAssignmentRepositoryMock: CommentEmotionAssignmentRepository = mockk()
            val getTokenFlowUseCaseMock: GetTokenFlowUseCase = mockk()

            coEvery { getTokenFlowUseCaseMock() } returns GetTokenFlowResult.Success(
                tokenFlow = flow { emit(null) }
            )

            val useCase = ProdSaveLabeledCommentsUseCase(
                commentEmotionAssignmentRepository = commentEmotionAssignmentRepositoryMock,
                getTokenFlowUseCase = getTokenFlowUseCaseMock,
            )

            val result = useCase(commentsToStore)

            val expectedResult = SaveLabeledCommentsResult.Failure.NoToken

            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun useCase_invokedWithGetTokenFlowReturningTokenAndRepositoryReturningServiceUnavailableException_returnsServiceUnavailableSaveLabeledCommentsFailure() =
        runTest {
            val numberOfComments = 10
            val userId = "userId"
            val authToken = "authToken"
            val refreshToken = "refreshToken"
            val id = "id"
            val text = "text"
            val emotion = Emotion.Anger
            val retrievedToken = Token(
                userId = userId,
                accessToken = authToken,
                refreshToken = refreshToken,
            )
            val commentToStore = Comment(
                id = id,
                text = text,
                emotion = emotion
            )
            val commentEmotionAssignmentInput = CommentEmotionAssignmentInput(
                userId = userId,
                commentId = id,
                emotion = emotion.toUppercaseString()
            )
            val commentsToStore = List(numberOfComments) { commentToStore }
            val commentEmotionAssignmentInputs =
                List(numberOfComments) { commentEmotionAssignmentInput }

            val commentEmotionAssignmentRepositoryMock: CommentEmotionAssignmentRepository = mockk()
            val getTokenFlowUseCaseMock: GetTokenFlowUseCase = mockk()

            coEvery { getTokenFlowUseCaseMock() } returns GetTokenFlowResult.Success(
                tokenFlow = flow {
                    emit(retrievedToken)
                }
            )

            coEvery {
                commentEmotionAssignmentRepositoryMock.postCommentEmotionAssignments(
                    commentEmotionAssignmentInputs = commentEmotionAssignmentInputs
                )
            } returns DataResult.Failure(ServiceUnavailableException(null, null))

            val useCase = ProdSaveLabeledCommentsUseCase(
                commentEmotionAssignmentRepository = commentEmotionAssignmentRepositoryMock,
                getTokenFlowUseCase = getTokenFlowUseCaseMock,
            )

            val result = useCase(commentsToStore)

            val expectedResult = SaveLabeledCommentsResult.Failure.ServiceUnavailable

            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun useCase_invokedWithGetTokenFlowReturningTokenAndRepositoryReturningNetworkException_returnsNetworkSaveLabeledCommentsFailure() =
        runTest {
            val numberOfComments = 10
            val userId = "userId"
            val authToken = "authToken"
            val refreshToken = "refreshToken"
            val id = "id"
            val text = "text"
            val emotion = Emotion.Anger
            val retrievedToken = Token(
                userId = userId,
                accessToken = authToken,
                refreshToken = refreshToken,
            )
            val commentToStore = Comment(
                id = id,
                text = text,
                emotion = emotion
            )
            val commentEmotionAssignmentInput = CommentEmotionAssignmentInput(
                userId = userId,
                commentId = id,
                emotion = emotion.toUppercaseString()
            )
            val commentsToStore = List(numberOfComments) { commentToStore }
            val commentEmotionAssignmentInputs =
                List(numberOfComments) { commentEmotionAssignmentInput }

            val commentEmotionAssignmentRepositoryMock: CommentEmotionAssignmentRepository = mockk()
            val getTokenFlowUseCaseMock: GetTokenFlowUseCase = mockk()

            coEvery { getTokenFlowUseCaseMock() } returns GetTokenFlowResult.Success(
                tokenFlow = flow {
                    emit(retrievedToken)
                }
            )

            coEvery {
                commentEmotionAssignmentRepositoryMock.postCommentEmotionAssignments(
                    commentEmotionAssignmentInputs = commentEmotionAssignmentInputs
                )
            } returns DataResult.Failure(NetworkException(null, null))

            val useCase = ProdSaveLabeledCommentsUseCase(
                commentEmotionAssignmentRepository = commentEmotionAssignmentRepositoryMock,
                getTokenFlowUseCase = getTokenFlowUseCaseMock,
            )

            val result = useCase(commentsToStore)

            val expectedResult = SaveLabeledCommentsResult.Failure.Network

            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun useCase_invokedWithGetTokenFlowReturningTokenAndRepositoryReturningUnauthorizedException_returnsUnauthorizedSaveLabeledCommentsFailure() =
        runTest {
            val numberOfComments = 10
            val userId = "userId"
            val authToken = "authToken"
            val refreshToken = "refreshToken"
            val id = "id"
            val text = "text"
            val emotion = Emotion.Anger
            val retrievedToken = Token(
                userId = userId,
                accessToken = authToken,
                refreshToken = refreshToken,
            )
            val commentToStore = Comment(
                id = id,
                text = text,
                emotion = emotion
            )
            val commentEmotionAssignmentInput = CommentEmotionAssignmentInput(
                userId = userId,
                commentId = id,
                emotion = emotion.toUppercaseString()
            )
            val commentsToStore = List(numberOfComments) { commentToStore }
            val commentEmotionAssignmentInputs =
                List(numberOfComments) { commentEmotionAssignmentInput }

            val commentEmotionAssignmentRepositoryMock: CommentEmotionAssignmentRepository = mockk()
            val getTokenFlowUseCaseMock: GetTokenFlowUseCase = mockk()

            coEvery { getTokenFlowUseCaseMock() } returns GetTokenFlowResult.Success(
                tokenFlow = flow {
                    emit(retrievedToken)
                }
            )

            coEvery {
                commentEmotionAssignmentRepositoryMock.postCommentEmotionAssignments(
                    commentEmotionAssignmentInputs = commentEmotionAssignmentInputs
                )
            } returns DataResult.Failure(UnauthorizedException(null, null))

            val useCase = ProdSaveLabeledCommentsUseCase(
                commentEmotionAssignmentRepository = commentEmotionAssignmentRepositoryMock,
                getTokenFlowUseCase = getTokenFlowUseCaseMock,
            )

            val result = useCase(commentsToStore)

            val expectedResult = SaveLabeledCommentsResult.Failure.UnauthorizedUser

            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun useCase_invokedWithGetTokenFlowReturningTokenAndRepositoryReturningWrongEmotionException_returnsWrongEmotionSaveLabeledCommentsFailure() =
        runTest {
            val errorMessage = "errorMessage"
            val numberOfComments = 10
            val userId = "userId"
            val authToken = "authToken"
            val refreshToken = "refreshToken"
            val id = "id"
            val text = "text"
            val emotion = Emotion.Anger
            val retrievedToken = Token(
                userId = userId,
                accessToken = authToken,
                refreshToken = refreshToken,
            )
            val commentToStore = Comment(
                id = id,
                text = text,
                emotion = emotion
            )
            val commentEmotionAssignmentInput = CommentEmotionAssignmentInput(
                userId = userId,
                commentId = id,
                emotion = emotion.toUppercaseString()
            )
            val commentsToStore = List(numberOfComments) { commentToStore }
            val commentEmotionAssignmentInputs =
                List(numberOfComments) { commentEmotionAssignmentInput }

            val commentEmotionAssignmentRepositoryMock: CommentEmotionAssignmentRepository = mockk()
            val getTokenFlowUseCaseMock: GetTokenFlowUseCase = mockk()

            coEvery { getTokenFlowUseCaseMock() } returns GetTokenFlowResult.Success(
                tokenFlow = flow {
                    emit(retrievedToken)
                }
            )

            coEvery {
                commentEmotionAssignmentRepositoryMock.postCommentEmotionAssignments(
                    commentEmotionAssignmentInputs = commentEmotionAssignmentInputs
                )
            } returns DataResult.Failure(CommentEmotionAssignmentException.WrongEmotion(errorMessage))

            val useCase = ProdSaveLabeledCommentsUseCase(
                commentEmotionAssignmentRepository = commentEmotionAssignmentRepositoryMock,
                getTokenFlowUseCase = getTokenFlowUseCaseMock,
            )

            val result = useCase(commentsToStore)

            val expectedResult = SaveLabeledCommentsResult.Failure.WrongEmotionParsing

            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun useCase_invokedWithGetTokenFlowReturningTokenAndRepositoryReturningAssignmentAlreadyExistsException_returnsAlreadyAssignedByThisUserSaveLabeledCommentsFailure() =
        runTest {
            val errorMessage = "errorMessage"
            val numberOfComments = 10
            val userId = "userId"
            val authToken = "authToken"
            val refreshToken = "refreshToken"
            val id = "id"
            val text = "text"
            val emotion = Emotion.Anger
            val retrievedToken = Token(
                userId = userId,
                accessToken = authToken,
                refreshToken = refreshToken,
            )
            val commentToStore = Comment(
                id = id,
                text = text,
                emotion = emotion
            )
            val commentEmotionAssignmentInput = CommentEmotionAssignmentInput(
                userId = userId,
                commentId = id,
                emotion = emotion.toUppercaseString()
            )
            val commentsToStore = List(numberOfComments) { commentToStore }
            val commentEmotionAssignmentInputs =
                List(numberOfComments) { commentEmotionAssignmentInput }

            val commentEmotionAssignmentRepositoryMock: CommentEmotionAssignmentRepository = mockk()
            val getTokenFlowUseCaseMock: GetTokenFlowUseCase = mockk()

            coEvery { getTokenFlowUseCaseMock() } returns GetTokenFlowResult.Success(
                tokenFlow = flow {
                    emit(retrievedToken)
                }
            )

            coEvery {
                commentEmotionAssignmentRepositoryMock.postCommentEmotionAssignments(
                    commentEmotionAssignmentInputs = commentEmotionAssignmentInputs
                )
            } returns DataResult.Failure(
                CommentEmotionAssignmentException.AssignmentAlreadyExists(
                errorMessage))

            val useCase = ProdSaveLabeledCommentsUseCase(
                commentEmotionAssignmentRepository = commentEmotionAssignmentRepositoryMock,
                getTokenFlowUseCase = getTokenFlowUseCaseMock,
            )

            val result = useCase(commentsToStore)

            val expectedResult = SaveLabeledCommentsResult.Failure.AlreadyAssignedByThisUser

            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun useCase_invokedWithUnlabeledComment_returnsNonLabeledCommentsSaveLabeledCommentsFailure() =
        runTest {
            val numberOfComments = 10
            val id = "id"
            val text = "text"
            val commentToStore = Comment(
                id = id,
                text = text,
                emotion = null
            )
            val commentsToStore = List(numberOfComments) { commentToStore }

            val commentEmotionAssignmentRepositoryMock: CommentEmotionAssignmentRepository = mockk()
            val getTokenFlowUseCaseMock: GetTokenFlowUseCase = mockk()

            val useCase = ProdSaveLabeledCommentsUseCase(
                commentEmotionAssignmentRepository = commentEmotionAssignmentRepositoryMock,
                getTokenFlowUseCase = getTokenFlowUseCaseMock,
            )

            val result = useCase(commentsToStore) as SaveLabeledCommentsResult.Failure.NonLabeledComments

            Truth.assertThat(result.e).isInstanceOf(IllegalArgumentException::class.java)
        }

    @Test
    fun useCase_invokedWithGetTokenFlowReturningTokenAndRepositoryReturningUnknownException_returnsUnknownSaveLabeledCommentsFailure() =
        runTest {
            val errorMessage = "errorMessage"
            val numberOfComments = 10
            val userId = "userId"
            val authToken = "authToken"
            val refreshToken = "refreshToken"
            val id = "id"
            val text = "text"
            val emotion = Emotion.Anger
            val retrievedToken = Token(
                userId = userId,
                accessToken = authToken,
                refreshToken = refreshToken,
            )
            val commentToStore = Comment(
                id = id,
                text = text,
                emotion = emotion
            )
            val commentEmotionAssignmentInput = CommentEmotionAssignmentInput(
                userId = userId,
                commentId = id,
                emotion = emotion.toUppercaseString()
            )
            val commentsToStore = List(numberOfComments) { commentToStore }
            val commentEmotionAssignmentInputs =
                List(numberOfComments) { commentEmotionAssignmentInput }

            val commentEmotionAssignmentRepositoryMock: CommentEmotionAssignmentRepository = mockk()
            val getTokenFlowUseCaseMock: GetTokenFlowUseCase = mockk()

            coEvery { getTokenFlowUseCaseMock() } returns GetTokenFlowResult.Success(
                tokenFlow = flow {
                    emit(retrievedToken)
                }
            )

            coEvery {
                commentEmotionAssignmentRepositoryMock.postCommentEmotionAssignments(
                    commentEmotionAssignmentInputs = commentEmotionAssignmentInputs
                )
            } returns DataResult.Failure(CommentEmotionAssignmentException.Unknown(errorMessage))

            val useCase = ProdSaveLabeledCommentsUseCase(
                commentEmotionAssignmentRepository = commentEmotionAssignmentRepositoryMock,
                getTokenFlowUseCase = getTokenFlowUseCaseMock,
            )

            val result = useCase(commentsToStore)

            val expectedResult = SaveLabeledCommentsResult.Failure.Unknown

            Truth.assertThat(result).isEqualTo(expectedResult)
        }
}
