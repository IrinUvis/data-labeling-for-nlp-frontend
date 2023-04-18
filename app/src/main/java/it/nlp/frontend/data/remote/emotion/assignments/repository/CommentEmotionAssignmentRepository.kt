package it.nlp.frontend.data.remote.emotion.assignments.repository

import it.nlp.frontend.data.core.model.DataResult
import it.nlp.frontend.data.remote.emotion.assignments.model.dto.CommentEmotionAssignmentInput
import it.nlp.frontend.data.remote.emotion.assignments.model.dto.CommentEmotionAssignmentOutput
import it.nlp.frontend.data.remote.model.exception.ApiException

interface CommentEmotionAssignmentRepository {
    suspend fun postCommentEmotionAssignments(
        commentEmotionAssignmentInputs: List<CommentEmotionAssignmentInput>
    ) : DataResult<List<CommentEmotionAssignmentOutput>, ApiException>
}
