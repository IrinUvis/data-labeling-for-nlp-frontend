package it.nlp.frontend.data.remote.emotion.assignments.repository

import it.nlp.frontend.data.remote.emotion.assignments.model.dto.TextEmotionAssignmentInput
import it.nlp.frontend.data.remote.emotion.assignments.model.dto.TextEmotionAssignmentNumberOutput
import it.nlp.frontend.data.remote.emotion.assignments.model.dto.TextEmotionAssignmentOutput
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TextEmotionAssignmentService {
    companion object {
        private const val URL = "emotion-assignments"
    }

    @POST(URL)
    suspend fun postAssignments(
        @Body assignmentInputs: List<TextEmotionAssignmentInput>
    ): Response<List<TextEmotionAssignmentOutput>>

    @GET("$URL/count")
    suspend fun getAssignmentsCount(): Response<TextEmotionAssignmentNumberOutput>
}
