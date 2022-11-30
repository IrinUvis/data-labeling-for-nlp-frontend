package it.winter2223.bachelor.ak.frontend.domain.comments.usecase.impl

import it.winter2223.bachelor.ak.frontend.data.remote.comment.repository.CommentRepository
import it.winter2223.bachelor.ak.frontend.domain.comments.model.Comment
import it.winter2223.bachelor.ak.frontend.domain.comments.model.GetCommentsToLabelResult
import it.winter2223.bachelor.ak.frontend.domain.comments.usecase.GetCommentsToLabelUseCase
import javax.inject.Inject

class ProdGetCommentsToLabelUseCase @Inject constructor(
    private val commentsRepository: CommentRepository,
) : GetCommentsToLabelUseCase {
    override suspend fun invoke(quantity: Int): GetCommentsToLabelResult {
        val comments = commentsRepository.fetchComments(quantity = quantity)

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
            onFailure = {
                GetCommentsToLabelResult.Failure
            }
        )
    }
}
