package it.nlp.frontend.data.remote.emotion.texts.repository

import it.nlp.frontend.data.remote.model.exception.ApiException
import it.nlp.frontend.data.core.model.DataResult
import it.nlp.frontend.data.remote.emotion.texts.model.dto.EmotionTextOutput

interface EmotionTextRepository {
    suspend fun fetchEmotionTextsToBeAssigned(
        userId: String,
        emotionTextsNumber: Int,
    ): DataResult<List<EmotionTextOutput>, ApiException>
}
