package it.nlp.frontend.data.remote.emotion.assignments.model.dto

data class TextEmotionAssignmentOutput(
    val userId: String,
    val textId: String,
    val emotionDto: EmotionDto,
)
