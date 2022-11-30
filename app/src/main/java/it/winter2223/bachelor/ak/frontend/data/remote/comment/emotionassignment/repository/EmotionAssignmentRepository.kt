package it.winter2223.bachelor.ak.frontend.data.remote.comment.emotionassignment.repository

import it.winter2223.bachelor.ak.frontend.data.remote.comment.emotionassignment.model.dto.CommentEmotionAssignmentInput
import it.winter2223.bachelor.ak.frontend.data.remote.comment.emotionassignment.model.dto.CommentEmotionAssignmentOutput

interface EmotionAssignmentRepository {
    suspend fun postCommentEmotionAssignment(
        commentEmotionAssignmentInputs: List<CommentEmotionAssignmentInput>
    ) : Result<List<CommentEmotionAssignmentOutput>>
}
