package it.winter2223.bachelor.ak.frontend.data.comments.model.dto

data class CommentEmotionAssignmentInput(
    val userId: String,
    val commentId: String,
    val emotion: String,
)
