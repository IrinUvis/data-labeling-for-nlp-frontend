package it.winter2223.bachelor.ak.frontend.domain.comments.usecase.impl

import it.winter2223.bachelor.ak.frontend.data.remote.comment.model.exception.CommentException
import it.winter2223.bachelor.ak.frontend.data.remote.comment.repository.CommentRepository
import it.winter2223.bachelor.ak.frontend.data.remote.model.exception.NetworkException
import it.winter2223.bachelor.ak.frontend.domain.comments.model.Comment
import it.winter2223.bachelor.ak.frontend.domain.comments.model.GetCommentsToLabelResult
import it.winter2223.bachelor.ak.frontend.domain.comments.usecase.GetCommentsToLabelUseCase
import javax.inject.Inject

class ProdGetCommentsToLabelUseCase @Inject constructor(
    private val commentsRepository: CommentRepository,
) : GetCommentsToLabelUseCase {
    override suspend fun invoke(quantity: Int): GetCommentsToLabelResult {
        val comments = commentsRepository.fetchComments(
            userId = "", // TODO: get it from data store
            commentsNumber = quantity,
        )

        return comments.fold(
            onSuccess = { commentDtos ->
                GetCommentsToLabelResult.Success(
                    comments = commentDtos.map { dto ->
                        Comment(
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
