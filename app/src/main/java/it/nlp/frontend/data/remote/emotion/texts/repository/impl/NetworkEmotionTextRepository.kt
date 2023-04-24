package it.nlp.frontend.data.remote.emotion.texts.repository.impl

import it.nlp.frontend.data.remote.core.ApiClient
import it.nlp.frontend.data.remote.emotion.texts.model.dto.EmotionTextOutput
import it.nlp.frontend.data.remote.emotion.texts.repository.EmotionTextRepository
import it.nlp.frontend.data.remote.emotion.texts.repository.EmotionTextService
import it.nlp.frontend.data.remote.model.ApiResponse
import javax.inject.Inject

class NetworkEmotionTextRepository @Inject constructor(
    private val apiClient: ApiClient,
    private val emotionTextService: EmotionTextService,
): EmotionTextRepository {
    override suspend fun getTextsForAssignment(quantity: Int): ApiResponse<List<EmotionTextOutput>> =
        apiClient.makeRequest { emotionTextService.getTextsForAssignment(quantity) }
}
