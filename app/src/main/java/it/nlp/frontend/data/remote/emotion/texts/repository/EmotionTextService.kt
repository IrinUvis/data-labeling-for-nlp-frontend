package it.nlp.frontend.data.remote.emotion.texts.repository

import it.nlp.frontend.data.remote.emotion.texts.model.dto.EmotionTextOutput
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface EmotionTextService {
    companion object {
        private const val URL = "emotion-texts"
        private const val EMOTION_TEXTS_NUMBER_PARAMETER = "emotionTextsNumber"
    }

    @GET(URL)
    suspend fun getTextsForAssignment(
        @Query(EMOTION_TEXTS_NUMBER_PARAMETER) quantity: Int
    ): Response<List<EmotionTextOutput>>
}
