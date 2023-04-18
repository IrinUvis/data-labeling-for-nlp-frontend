package it.nlp.frontend.data.remote.emotion.assignments.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TextEmotionAssignmentOutput(
    @SerialName("userId")
    val userId: String,

    @SerialName("textId")
    val textId: String,

    @SerialName("emotionDto")
    val emotionDto: EmotionDto,
)
