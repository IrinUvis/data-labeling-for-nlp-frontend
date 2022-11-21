package it.winter2223.bachelor.ak.frontend.data.comments.repository

import it.winter2223.bachelor.ak.frontend.data.comments.model.dto.CommentEmotionAssignmentInput
import it.winter2223.bachelor.ak.frontend.data.comments.model.dto.CommentEmotionAssignmentOutput

interface EmotionAssignmentRepository {
    suspend fun postCommentEmotionAssignment(
        commentEmotionAssignmentInputs: List<CommentEmotionAssignmentInput>
    ) : Result<List<CommentEmotionAssignmentOutput>>
}
