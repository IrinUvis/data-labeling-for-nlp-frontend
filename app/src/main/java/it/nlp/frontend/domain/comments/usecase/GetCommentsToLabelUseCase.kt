package it.nlp.frontend.domain.comments.usecase

import it.nlp.frontend.domain.comments.model.GetCommentsToLabelResult

interface GetCommentsToLabelUseCase {
    suspend operator fun invoke(quantity: Int): GetCommentsToLabelResult
}
