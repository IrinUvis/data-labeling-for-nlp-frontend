package it.nlp.frontend.data.remote.emotion.assignments.repository

import it.nlp.frontend.data.core.model.DataResult
import it.nlp.frontend.data.remote.emotion.assignments.model.dto.TextEmotionAssignmentInput
import it.nlp.frontend.data.remote.emotion.assignments.model.dto.TextEmotionAssignmentNumberOutput
import it.nlp.frontend.data.remote.emotion.assignments.model.dto.TextEmotionAssignmentOutput
import it.nlp.frontend.data.remote.model.exception.ApiException

interface TextEmotionAssignmentRepository {
    suspend fun postTextEmotionAssignments(
        textEmotionAssignmentInputs: List<TextEmotionAssignmentInput>
    ): DataResult<List<TextEmotionAssignmentOutput>, ApiException>

    suspend fun getNumberOfTextEmotionAssignments(): DataResult<TextEmotionAssignmentNumberOutput, ApiException>
}
