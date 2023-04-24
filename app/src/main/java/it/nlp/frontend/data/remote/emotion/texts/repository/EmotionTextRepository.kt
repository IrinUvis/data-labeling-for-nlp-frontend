package it.nlp.frontend.data.remote.emotion.texts.repository

import it.nlp.frontend.data.remote.emotion.texts.model.dto.EmotionTextOutput
import it.nlp.frontend.data.remote.model.ApiResponse

interface EmotionTextRepository {
    suspend fun getTextsForAssignment(quantity: Int): ApiResponse<List<EmotionTextOutput>>
}
