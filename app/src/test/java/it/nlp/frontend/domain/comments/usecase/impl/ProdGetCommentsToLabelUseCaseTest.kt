@file:Suppress("MaxLineLength")

package it.nlp.frontend.domain.comments.usecase.impl

import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.mockk
import it.nlp.frontend.data.core.model.DataResult
import it.nlp.frontend.data.remote.comment.model.dto.CommentOutput
import it.nlp.frontend.data.remote.comment.model.exception.CommentException
import it.nlp.frontend.data.remote.comment.repository.CommentRepository
import it.nlp.frontend.data.remote.model.exception.NetworkException
import it.nlp.frontend.data.remote.model.exception.ServiceUnavailableException
import it.nlp.frontend.data.remote.model.exception.UnauthorizedException
import it.nlp.frontend.domain.comments.model.Comment
import it.nlp.frontend.domain.comments.model.GetCommentsToLabelResult
import it.nlp.frontend.domain.token.model.GetTokenFlowResult
import it.nlp.frontend.domain.token.model.Token
import it.nlp.frontend.domain.token.usecase.GetTokenFlowUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class ProdGetCommentsToLabelUseCaseTest {

    @Test
    fun useCase_invokedWithGetTokenFlowReturningTokenAndRepositoryReturningSuccess_returnsGetCommentsToLabelSuccess() =
        runTest {
            val authToken = "authToken"
            val refreshToken = "refreshToken"
            val userId = "userId"
            val commentsNumber = 10
            val commentId = "commentId"
            val commentContent = "content"

            val commentOutput = CommentOutput(
                commentId = commentId,
                content = commentContent
            )
            val comment = Comment(
                id = commentId,
                text = commentContent,
            )
            val commentsOutputList = List(commentsNumber) { commentOutput }
            val commentsList = List(commentsNumber) { comment }

            val retrievedToken = Token(
                authToken = authToken,
                refreshToken = refreshToken,
                userId = userId
            )

            val commentsRepositoryMock: CommentRepository = mockk()
            val getTokenFlowUseCaseMock: GetTokenFlowUseCase = mockk()

            coEvery { getTokenFlowUseCaseMock() } returns GetTokenFlowResult.Success(
                tokenFlow = flow { emit(retrievedToken) }
            )
            coEvery {
                commentsRepositoryMock.fetchComments(
                    userId = userId,
                    commentsNumber = commentsNumber,
                )
            } returns DataResult.Success(commentsOutputList)

            val useCase = ProdGetCommentsToLabelUseCase(
                commentsRepository = commentsRepositoryMock,
                getTokenFlowUseCase = getTokenFlowUseCaseMock
            )

            val result = useCase(commentsNumber)

            val expectedResult = GetCommentsToLabelResult.Success(commentsList)

            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun useCase_invokedWithGetTokenReturningFailure_returnsReadingTokenGetCommentsToLabelResult() =
        runTest {
            val commentsNumber = 10

            val commentsRepositoryMock: CommentRepository = mockk()
            val getTokenFlowUseCaseMock: GetTokenFlowUseCase = mockk()

            coEvery { getTokenFlowUseCaseMock() } returns GetTokenFlowResult.Failure(IOException())

            val useCase = ProdGetCommentsToLabelUseCase(
                commentsRepository = commentsRepositoryMock,
                getTokenFlowUseCase = getTokenFlowUseCaseMock
            )

            val result = useCase(commentsNumber)

            val expectedResult = GetCommentsToLabelResult.Failure.ReadingToken

            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun useCase_invokedWithGetTokenReturningNull_returnNoTokenGetCommentsToLabelResult() = runTest {
        val commentsNumber = 10

        val commentsRepositoryMock: CommentRepository = mockk()
        val getTokenFlowUseCaseMock: GetTokenFlowUseCase = mockk()

        coEvery { getTokenFlowUseCaseMock() } returns GetTokenFlowResult.Success(
            tokenFlow = flow { emit(null) }
        )

        val useCase = ProdGetCommentsToLabelUseCase(
            commentsRepository = commentsRepositoryMock,
            getTokenFlowUseCase = getTokenFlowUseCaseMock
        )

        val result = useCase(commentsNumber)

        val expectedResult = GetCommentsToLabelResult.Failure.NoToken

        Truth.assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun useCase_invokedWithGetTokenReturningTokenAndRepositoryReturningEmptyList_returnNoCommentsGetCommentsToLabelResult() =
        runTest {
            val authToken = "authToken"
            val refreshToken = "refreshToken"
            val userId = "userId"
            val commentsNumber = 10

            val commentsOutputList = emptyList<CommentOutput>()

            val retrievedToken = Token(
                authToken = authToken,
                refreshToken = refreshToken,
                userId = userId
            )

            val commentsRepositoryMock: CommentRepository = mockk()
            val getTokenFlowUseCaseMock: GetTokenFlowUseCase = mockk()

            coEvery { getTokenFlowUseCaseMock() } returns GetTokenFlowResult.Success(
                tokenFlow = flow { emit(retrievedToken) }
            )
            coEvery {
                commentsRepositoryMock.fetchComments(
                    userId = userId,
                    commentsNumber = commentsNumber,
                )
            } returns DataResult.Success(commentsOutputList)

            val useCase = ProdGetCommentsToLabelUseCase(
                commentsRepository = commentsRepositoryMock,
                getTokenFlowUseCase = getTokenFlowUseCaseMock
            )

            val result = useCase(commentsNumber)

            val expectedResult = GetCommentsToLabelResult.Failure.NoComments

            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun useCase_invokedWithGetTokenReturningTokenAndRepositoryReturningUnauthorizedException_returnUnauthorizedUserGetCommentsToLabelResult() =
        runTest {
            val authToken = "authToken"
            val refreshToken = "refreshToken"
            val userId = "userId"
            val commentsNumber = 10

            val retrievedToken = Token(
                authToken = authToken,
                refreshToken = refreshToken,
                userId = userId
            )

            val commentsRepositoryMock: CommentRepository = mockk()
            val getTokenFlowUseCaseMock: GetTokenFlowUseCase = mockk()

            coEvery { getTokenFlowUseCaseMock() } returns GetTokenFlowResult.Success(
                tokenFlow = flow { emit(retrievedToken) }
            )
            coEvery {
                commentsRepositoryMock.fetchComments(
                    userId = userId,
                    commentsNumber = commentsNumber,
                )
            } returns DataResult.Failure(UnauthorizedException(null, null))

            val useCase = ProdGetCommentsToLabelUseCase(
                commentsRepository = commentsRepositoryMock,
                getTokenFlowUseCase = getTokenFlowUseCaseMock
            )

            val result = useCase(commentsNumber)

            val expectedResult = GetCommentsToLabelResult.Failure.UnauthorizedUser

            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun useCase_invokedWithGetTokenReturningTokenAndRepositoryReturningNetworkException_returnNetworkGetCommentsToLabelResult() =
        runTest {
            val authToken = "authToken"
            val refreshToken = "refreshToken"
            val userId = "userId"
            val commentsNumber = 10

            val retrievedToken = Token(
                authToken = authToken,
                refreshToken = refreshToken,
                userId = userId
            )

            val commentsRepositoryMock: CommentRepository = mockk()
            val getTokenFlowUseCaseMock: GetTokenFlowUseCase = mockk()

            coEvery { getTokenFlowUseCaseMock() } returns GetTokenFlowResult.Success(
                tokenFlow = flow { emit(retrievedToken) }
            )
            coEvery {
                commentsRepositoryMock.fetchComments(
                    userId = userId,
                    commentsNumber = commentsNumber,
                )
            } returns DataResult.Failure(NetworkException(null, null))

            val useCase = ProdGetCommentsToLabelUseCase(
                commentsRepository = commentsRepositoryMock,
                getTokenFlowUseCase = getTokenFlowUseCaseMock
            )

            val result = useCase(commentsNumber)

            val expectedResult = GetCommentsToLabelResult.Failure.Network

            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun useCase_invokedWithGetTokenReturningTokenAndRepositoryReturningServiceUnavailableException_returnServiceUnavailableGetCommentsToLabelResult() =
        runTest {
            val authToken = "authToken"
            val refreshToken = "refreshToken"
            val userId = "userId"
            val commentsNumber = 10

            val retrievedToken = Token(
                authToken = authToken,
                refreshToken = refreshToken,
                userId = userId
            )

            val commentsRepositoryMock: CommentRepository = mockk()
            val getTokenFlowUseCaseMock: GetTokenFlowUseCase = mockk()

            coEvery { getTokenFlowUseCaseMock() } returns GetTokenFlowResult.Success(
                tokenFlow = flow { emit(retrievedToken) }
            )
            coEvery {
                commentsRepositoryMock.fetchComments(
                    userId = userId,
                    commentsNumber = commentsNumber,
                )
            } returns DataResult.Failure(ServiceUnavailableException(null, null))

            val useCase = ProdGetCommentsToLabelUseCase(
                commentsRepository = commentsRepositoryMock,
                getTokenFlowUseCase = getTokenFlowUseCaseMock
            )

            val result = useCase(commentsNumber)

            val expectedResult = GetCommentsToLabelResult.Failure.ServiceUnavailable

            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun useCase_invokedWithGetTokenReturningTokenAndRepositoryReturningCommentsNumberOutOfRangeException_returnCommentsNumberOutOfRangeGetCommentsToLabelResult() =
        runTest {
            val authToken = "authToken"
            val refreshToken = "refreshToken"
            val userId = "userId"
            val commentsNumber = 10
            val errorMessage = "errorMessage"

            val retrievedToken = Token(
                authToken = authToken,
                refreshToken = refreshToken,
                userId = userId
            )

            val commentsRepositoryMock: CommentRepository = mockk()
            val getTokenFlowUseCaseMock: GetTokenFlowUseCase = mockk()

            coEvery { getTokenFlowUseCaseMock() } returns GetTokenFlowResult.Success(
                tokenFlow = flow { emit(retrievedToken) }
            )
            coEvery {
                commentsRepositoryMock.fetchComments(
                    userId = userId,
                    commentsNumber = commentsNumber,
                )
            } returns DataResult.Failure(CommentException.CommentsNumberOutOfRange(errorMessage))

            val useCase = ProdGetCommentsToLabelUseCase(
                commentsRepository = commentsRepositoryMock,
                getTokenFlowUseCase = getTokenFlowUseCaseMock
            )

            val result = useCase(commentsNumber)

            val expectedResult = GetCommentsToLabelResult.Failure.CommentsNumberOutOfRange

            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun useCase_invokedWithGetTokenReturningTokenAndRepositoryReturningNoCommentWithEnteredIdException_returnUnknownGetCommentsToLabelResult() =
        runTest {
            val authToken = "authToken"
            val refreshToken = "refreshToken"
            val userId = "userId"
            val commentsNumber = 10
            val errorMessage = "errorMessage"

            val retrievedToken = Token(
                authToken = authToken,
                refreshToken = refreshToken,
                userId = userId
            )

            val commentsRepositoryMock: CommentRepository = mockk()
            val getTokenFlowUseCaseMock: GetTokenFlowUseCase = mockk()

            coEvery { getTokenFlowUseCaseMock() } returns GetTokenFlowResult.Success(
                tokenFlow = flow { emit(retrievedToken) }
            )
            coEvery {
                commentsRepositoryMock.fetchComments(
                    userId = userId,
                    commentsNumber = commentsNumber,
                )
            } returns DataResult.Failure(CommentException.NoCommentWithEnteredId(errorMessage))

            val useCase = ProdGetCommentsToLabelUseCase(
                commentsRepository = commentsRepositoryMock,
                getTokenFlowUseCase = getTokenFlowUseCaseMock
            )

            val result = useCase(commentsNumber)

            val expectedResult = GetCommentsToLabelResult.Failure.Unknown

            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun useCase_invokedWithGetTokenReturningTokenAndRepositoryReturningCommentsNumberIsNotIntegerException_returnUnknownGetCommentsToLabelResult() =
        runTest {
            val authToken = "authToken"
            val refreshToken = "refreshToken"
            val userId = "userId"
            val commentsNumber = 10
            val errorMessage = "errorMessage"

            val retrievedToken = Token(
                authToken = authToken,
                refreshToken = refreshToken,
                userId = userId
            )

            val commentsRepositoryMock: CommentRepository = mockk()
            val getTokenFlowUseCaseMock: GetTokenFlowUseCase = mockk()

            coEvery { getTokenFlowUseCaseMock() } returns GetTokenFlowResult.Success(
                tokenFlow = flow { emit(retrievedToken) }
            )
            coEvery {
                commentsRepositoryMock.fetchComments(
                    userId = userId,
                    commentsNumber = commentsNumber,
                )
            } returns DataResult.Failure(CommentException.CommentsNumberIsNotInteger(errorMessage))

            val useCase = ProdGetCommentsToLabelUseCase(
                commentsRepository = commentsRepositoryMock,
                getTokenFlowUseCase = getTokenFlowUseCaseMock
            )

            val result = useCase(commentsNumber)

            val expectedResult = GetCommentsToLabelResult.Failure.Unknown

            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun useCase_invokedWithGetTokenReturningTokenAndRepositoryReturningCannotCompareNullCommentException_returnUnknownGetCommentsToLabelResult() =
        runTest {
            val authToken = "authToken"
            val refreshToken = "refreshToken"
            val userId = "userId"
            val commentsNumber = 10
            val errorMessage = "errorMessage"

            val retrievedToken = Token(
                authToken = authToken,
                refreshToken = refreshToken,
                userId = userId
            )

            val commentsRepositoryMock: CommentRepository = mockk()
            val getTokenFlowUseCaseMock: GetTokenFlowUseCase = mockk()

            coEvery { getTokenFlowUseCaseMock() } returns GetTokenFlowResult.Success(
                tokenFlow = flow { emit(retrievedToken) }
            )
            coEvery {
                commentsRepositoryMock.fetchComments(
                    userId = userId,
                    commentsNumber = commentsNumber,
                )
            } returns DataResult.Failure(CommentException.CannotCompareNullComment(errorMessage))

            val useCase = ProdGetCommentsToLabelUseCase(
                commentsRepository = commentsRepositoryMock,
                getTokenFlowUseCase = getTokenFlowUseCaseMock
            )

            val result = useCase(commentsNumber)

            val expectedResult = GetCommentsToLabelResult.Failure.Unknown

            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun useCase_invokedWithGetTokenReturningTokenAndRepositoryReturningVideosFetchingErrorException_returnUnknownGetCommentsToLabelResult() =
        runTest {
            val authToken = "authToken"
            val refreshToken = "refreshToken"
            val userId = "userId"
            val commentsNumber = 10
            val errorMessage = "errorMessage"

            val retrievedToken = Token(
                authToken = authToken,
                refreshToken = refreshToken,
                userId = userId
            )

            val commentsRepositoryMock: CommentRepository = mockk()
            val getTokenFlowUseCaseMock: GetTokenFlowUseCase = mockk()

            coEvery { getTokenFlowUseCaseMock() } returns GetTokenFlowResult.Success(
                tokenFlow = flow { emit(retrievedToken) }
            )
            coEvery {
                commentsRepositoryMock.fetchComments(
                    userId = userId,
                    commentsNumber = commentsNumber,
                )
            } returns DataResult.Failure(CommentException.VideosFetchingError(errorMessage))

            val useCase = ProdGetCommentsToLabelUseCase(
                commentsRepository = commentsRepositoryMock,
                getTokenFlowUseCase = getTokenFlowUseCaseMock
            )

            val result = useCase(commentsNumber)

            val expectedResult = GetCommentsToLabelResult.Failure.Unknown

            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun useCase_invokedWithGetTokenReturningTokenAndRepositoryReturningCommentsFetchingErrorException_returnUnknownGetCommentsToLabelResult() =
        runTest {
            val authToken = "authToken"
            val refreshToken = "refreshToken"
            val userId = "userId"
            val commentsNumber = 10
            val errorMessage = "errorMessage"

            val retrievedToken = Token(
                authToken = authToken,
                refreshToken = refreshToken,
                userId = userId
            )

            val commentsRepositoryMock: CommentRepository = mockk()
            val getTokenFlowUseCaseMock: GetTokenFlowUseCase = mockk()

            coEvery { getTokenFlowUseCaseMock() } returns GetTokenFlowResult.Success(
                tokenFlow = flow { emit(retrievedToken) }
            )
            coEvery {
                commentsRepositoryMock.fetchComments(
                    userId = userId,
                    commentsNumber = commentsNumber,
                )
            } returns DataResult.Failure(CommentException.CommentsFetchingError(errorMessage))

            val useCase = ProdGetCommentsToLabelUseCase(
                commentsRepository = commentsRepositoryMock,
                getTokenFlowUseCase = getTokenFlowUseCaseMock
            )

            val result = useCase(commentsNumber)

            val expectedResult = GetCommentsToLabelResult.Failure.Unknown

            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun useCase_invokedWithGetTokenReturningTokenAndRepositoryReturningUnknownException_returnUnknownGetCommentsToLabelResult() =
        runTest {
            val authToken = "authToken"
            val refreshToken = "refreshToken"
            val userId = "userId"
            val commentsNumber = 10
            val errorMessage = "errorMessage"

            val retrievedToken = Token(
                authToken = authToken,
                refreshToken = refreshToken,
                userId = userId
            )

            val commentsRepositoryMock: CommentRepository = mockk()
            val getTokenFlowUseCaseMock: GetTokenFlowUseCase = mockk()

            coEvery { getTokenFlowUseCaseMock() } returns GetTokenFlowResult.Success(
                tokenFlow = flow { emit(retrievedToken) }
            )
            coEvery {
                commentsRepositoryMock.fetchComments(
                    userId = userId,
                    commentsNumber = commentsNumber,
                )
            } returns DataResult.Failure(CommentException.Unknown(errorMessage))

            val useCase = ProdGetCommentsToLabelUseCase(
                commentsRepository = commentsRepositoryMock,
                getTokenFlowUseCase = getTokenFlowUseCaseMock
            )

            val result = useCase(commentsNumber)

            val expectedResult = GetCommentsToLabelResult.Failure.Unknown

            Truth.assertThat(result).isEqualTo(expectedResult)
        }
}
