package it.winter2223.bachelor.ak.frontend.domain.comments.usecase.impl

import it.winter2223.bachelor.ak.frontend.data.remote.comment.model.exception.CommentException
import it.winter2223.bachelor.ak.frontend.data.remote.comment.repository.CommentRepository
import it.winter2223.bachelor.ak.frontend.data.remote.model.exception.NetworkException
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
        val userId = when (val tokenFlowResult = getTokenFlowUseCase()) {
            is GetTokenFlowResult.Success -> {
                val token = tokenFlowResult.tokenFlow.first()
                token?.userId ?: return GetCommentsToLabelResult.Failure
            }
            is GetTokenFlowResult.Failure -> {
                return GetCommentsToLabelResult.Failure // TODO: Handle errors differently
            }
        }

        val comments = commentsRepository.fetchComments(
            userId = userId,
            commentsNumber = quantity,
        )

        return comments.fold(
            onSuccess = { commentDtos ->
                GetCommentsToLabelResult.Success(
                    comments = commentDtos.map { dto ->
                        Comment(
                            id = dto.commentId,
                            text = dto.content,
                        )
                    }
                )
            },
            onFailure = { apiException ->
                // TODO: Handle errors differently
                when (apiException) {
                    is NetworkException -> {
                        GetCommentsToLabelResult.Failure
                    }
                    is CommentException -> {
                        GetCommentsToLabelResult.Failure
                    }
                    else -> {
                        GetCommentsToLabelResult.Failure
                    }
                }
            }
        )
    }
}
