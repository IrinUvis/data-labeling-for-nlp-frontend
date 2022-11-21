package it.winter2223.bachelor.ak.frontend.data.comments.model.dto

import java.util.UUID

data class CommentEmotionAssignmentOutput(
    val assignmentId: UUID,
    val userId: String,
    val commentId: String,
    val emotionDto: EmotionDto,
)
