package it.winter2223.bachelor.ak.frontend.data.remote.comment.emotionassignment.repository

import it.winter2223.bachelor.ak.frontend.data.core.model.DataResult
import it.winter2223.bachelor.ak.frontend.data.remote.comment.emotionassignment.model.dto.CommentEmotionAssignmentInput
import it.winter2223.bachelor.ak.frontend.data.remote.comment.emotionassignment.model.dto.CommentEmotionAssignmentOutput
import it.winter2223.bachelor.ak.frontend.data.remote.model.exception.ApiException

interface CommentEmotionAssignmentRepository {
    suspend fun postCommentEmotionAssignments(
        commentEmotionAssignmentInputs: List<CommentEmotionAssignmentInput>
    ) : DataResult<List<CommentEmotionAssignmentOutput>, ApiException>
}
