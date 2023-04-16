package it.nlp.frontend.data.remote.comment.emotionassignment.repository

import it.nlp.frontend.data.core.model.DataResult
import it.nlp.frontend.data.remote.comment.emotionassignment.model.dto.CommentEmotionAssignmentInput
import it.nlp.frontend.data.remote.comment.emotionassignment.model.dto.CommentEmotionAssignmentOutput
import it.nlp.frontend.data.remote.model.exception.ApiException

interface CommentEmotionAssignmentRepository {
    suspend fun postCommentEmotionAssignments(
        commentEmotionAssignmentInputs: List<CommentEmotionAssignmentInput>
    ) : DataResult<List<CommentEmotionAssignmentOutput>, ApiException>
}
