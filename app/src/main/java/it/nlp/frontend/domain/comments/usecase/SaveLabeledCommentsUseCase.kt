package it.nlp.frontend.domain.comments.usecase

import it.nlp.frontend.domain.comments.model.Comment
import it.nlp.frontend.domain.comments.model.SaveLabeledCommentsResult

interface SaveLabeledCommentsUseCase {
    suspend operator fun invoke(comments: List<Comment>): SaveLabeledCommentsResult
}
