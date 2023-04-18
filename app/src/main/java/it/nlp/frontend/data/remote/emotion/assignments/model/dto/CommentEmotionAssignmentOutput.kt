package it.nlp.frontend.data.remote.emotion.assignments.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CommentEmotionAssignmentOutput(
    @SerialName("assignmentId")
    val assignmentId: String,

    @SerialName("userId")
    val userId: String,

    @SerialName("commentId")
    val commentId: String,

    @SerialName("emotionDto")
    val emotionDto: EmotionDto,
)
