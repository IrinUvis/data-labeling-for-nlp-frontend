package it.winter2223.bachelor.ak.frontend.domain.comments.usecase.impl

import it.winter2223.bachelor.ak.frontend.data.remote.comment.model.exception.CommentException
import it.winter2223.bachelor.ak.frontend.data.remote.comment.repository.CommentRepository
import it.winter2223.bachelor.ak.frontend.data.remote.model.exception.ApiException
import it.winter2223.bachelor.ak.frontend.data.remote.model.exception.NetworkException
import it.winter2223.bachelor.ak.frontend.data.remote.model.exception.UnauthorizedException
import it.winter2223.bachelor.ak.frontend.domain.comments.model.Comment
import it.winter2223.bachelor.ak.frontend.domain.comments.model.GetCommentsToLabelResult
import it.winter2223.bachelor.ak.frontend.domain.comments.usecase.GetCommentsToLabelUseCase
import it.winter2223.bachelor.ak.frontend.domain.token.model.GetTokenFlowResult
import it.winter2223.bachelor.ak.frontend.domain.token.usecase.GetTokenFlowUseCase
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ProdGetCommentsToLabelUseCase @Inject constructor(
    private val commentsRepository: CommentRepository,
    private val getTokenFlowUseCase: GetTokenFlowUseCase,
) : GetCommentsToLabelUseCase {
    override suspend fun invoke(quantity: Int): GetCommentsToLabelResult {
        return when (val tokenFlowResult = getTokenFlowUseCase()) {
            is GetTokenFlowResult.Success -> {
                val token = tokenFlowResult.tokenFlow.first()
                token?.userId?.let { userId ->
                    val commentsResult = commentsRepository.fetchComments(
                        userId = userId,
                        commentsNumber = quantity,
                    )

                    commentsResult.fold(
                        onSuccess = { commentDtos ->
                            if (commentDtos.isNotEmpty()) {
                                GetCommentsToLabelResult.Success(
                                    comments = commentDtos.map { dto ->
                                        Comment(
                                            id = dto.commentId,
                                            text = dto.content,
                                        )
                                    }
                                )
                            } else {
                                GetCommentsToLabelResult.Failure.NoComments
                            }
                        },
                        onFailure = { apiException ->
                            getCommentsToLabelResultForApiException(apiException)
                        }
                    )
                } ?: GetCommentsToLabelResult.Failure.NoToken
            }
            is GetTokenFlowResult.Failure -> {
                GetCommentsToLabelResult.Failure.ReadingToken
            }
        }
    }

    private fun getCommentsToLabelResultForApiException(apiException: ApiException): GetCommentsToLabelResult {
        return when (apiException) {
            is NetworkException -> GetCommentsToLabelResult.Failure.Network
            is UnauthorizedException -> GetCommentsToLabelResult.Failure.UnauthorizedUser
            is CommentException.CommentsNumberOutOfRange ->
                GetCommentsToLabelResult.Failure.CommentsNumberOutOfRange
            else -> GetCommentsToLabelResult.Failure.Unknown
        }
    }
}
