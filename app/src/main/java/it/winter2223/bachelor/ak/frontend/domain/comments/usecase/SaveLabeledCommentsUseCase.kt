package it.winter2223.bachelor.ak.frontend.domain.comments.usecase

import it.winter2223.bachelor.ak.frontend.domain.comments.model.Comment
import it.winter2223.bachelor.ak.frontend.domain.comments.model.SaveLabeledCommentsResult

interface SaveLabeledCommentsUseCase {
    suspend operator fun invoke(comments: List<Comment>): SaveLabeledCommentsResult
}
