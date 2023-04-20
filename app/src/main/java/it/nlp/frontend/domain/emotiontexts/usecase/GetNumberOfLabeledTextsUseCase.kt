package it.nlp.frontend.domain.emotiontexts.usecase

import it.nlp.frontend.domain.emotiontexts.model.GetNumberOfLabeledTextsResult

interface GetNumberOfLabeledTextsUseCase {
    suspend operator fun invoke(): GetNumberOfLabeledTextsResult
}
