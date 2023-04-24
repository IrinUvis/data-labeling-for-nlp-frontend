package it.nlp.frontend.data.remote.emotion.assignments.repository

import it.nlp.frontend.data.remote.emotion.assignments.model.dto.TextEmotionAssignmentInput
import it.nlp.frontend.data.remote.emotion.assignments.model.dto.TextEmotionAssignmentNumberOutput
import it.nlp.frontend.data.remote.emotion.assignments.model.dto.TextEmotionAssignmentOutput
import it.nlp.frontend.data.remote.model.ApiResponse

interface TextEmotionAssignmentRepository {
    suspend fun postAssignments(assignments: List<TextEmotionAssignmentInput>):
            ApiResponse<List<TextEmotionAssignmentOutput>>

    suspend fun getAssignmentsCount(): ApiResponse<TextEmotionAssignmentNumberOutput>
}
