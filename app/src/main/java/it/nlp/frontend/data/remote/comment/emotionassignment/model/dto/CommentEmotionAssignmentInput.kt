package it.nlp.frontend.data.remote.comment.emotionassignment.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CommentEmotionAssignmentInput(
    @SerialName("userId")
    val userId: String,

    @SerialName("commentId")
    val commentId: String,

    @SerialName("emotion")
    val emotion: String,
)
