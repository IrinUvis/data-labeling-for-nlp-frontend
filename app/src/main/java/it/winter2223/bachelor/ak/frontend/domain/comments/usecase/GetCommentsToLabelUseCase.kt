package it.winter2223.bachelor.ak.frontend.domain.comments.usecase

import it.winter2223.bachelor.ak.frontend.domain.comments.model.GetCommentsToLabelResult

interface GetCommentsToLabelUseCase {
    suspend operator fun invoke(quantity: Int): GetCommentsToLabelResult
}
