package it.nlp.frontend.data.remote.emotion.texts.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CommentOutput(
    @SerialName("commentId")
    val commentId: String,

    @SerialName("content")
    val content: String,
)
