package it.nlp.frontend.data.remote.emotion.texts.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EmotionTextOutput(
    @SerialName("emotionTextId")
    val id: String,

    @SerialName("content")
    val content: String,
)
