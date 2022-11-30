package it.winter2223.bachelor.ak.frontend.data.remote.comment.emotionassignment.model.dto

import it.winter2223.bachelor.ak.frontend.data.remote.comment.model.dto.EmotionDto
import java.util.UUID

data class CommentEmotionAssignmentOutput(
    val assignmentId: UUID,
    val userId: String,
    val commentId: String,
    val emotionDto: EmotionDto,
)
