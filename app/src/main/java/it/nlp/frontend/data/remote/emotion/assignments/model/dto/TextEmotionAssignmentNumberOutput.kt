package it.nlp.frontend.data.remote.emotion.assignments.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TextEmotionAssignmentNumberOutput(
    @SerialName("assignmentsCount")
    val assignmentsCount: Int
)
