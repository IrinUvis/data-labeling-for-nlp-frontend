package it.nlp.frontend.data.remote.emotion.assignments.repository.impl

import it.nlp.frontend.data.remote.core.ApiClient
import it.nlp.frontend.data.remote.emotion.assignments.model.dto.TextEmotionAssignmentInput
import it.nlp.frontend.data.remote.emotion.assignments.model.dto.TextEmotionAssignmentNumberOutput
import it.nlp.frontend.data.remote.emotion.assignments.model.dto.TextEmotionAssignmentOutput
import it.nlp.frontend.data.remote.emotion.assignments.repository.TextEmotionAssignmentRepository
import it.nlp.frontend.data.remote.emotion.assignments.repository.TextEmotionAssignmentService
import it.nlp.frontend.data.remote.model.ApiResponse
import javax.inject.Inject

class NetworkTextEmotionAssignmentRepository @Inject constructor(
    private val apiClient: ApiClient,
    private val textEmotionAssignmentService: TextEmotionAssignmentService
) : TextEmotionAssignmentRepository {
    override suspend fun postAssignments(
        assignments: List<TextEmotionAssignmentInput>
    ): ApiResponse<List<TextEmotionAssignmentOutput>> =
        apiClient.makeRequest { textEmotionAssignmentService.postAssignments(assignments) }

    override suspend fun getAssignmentsCount(): ApiResponse<TextEmotionAssignmentNumberOutput> =
        apiClient.makeRequest { textEmotionAssignmentService.getAssignmentsCount() }
}
