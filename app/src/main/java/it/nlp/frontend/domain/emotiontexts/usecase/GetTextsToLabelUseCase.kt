package it.nlp.frontend.domain.emotiontexts.usecase

import it.nlp.frontend.domain.emotiontexts.model.GetTextsToLabelResult

interface GetTextsToLabelUseCase {
    suspend operator fun invoke(quantity: Int): GetTextsToLabelResult
}
