package it.nlp.frontend.domain.emotiontexts.usecase

import it.nlp.frontend.domain.emotiontexts.model.EmotionText
import it.nlp.frontend.domain.emotiontexts.model.SaveLabeledTextsResult

interface SaveLabeledTextsUseCase {
    suspend operator fun invoke(emotionTexts: List<EmotionText>): SaveLabeledTextsResult
}
