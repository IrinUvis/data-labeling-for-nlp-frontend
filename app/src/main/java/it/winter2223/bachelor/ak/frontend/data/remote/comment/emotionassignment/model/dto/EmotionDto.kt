package it.winter2223.bachelor.ak.frontend.data.remote.comment.emotionassignment.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class EmotionDto {
    @SerialName("ANGER") Anger,
    @SerialName("FEAR") Fear,
    @SerialName("JOY") Joy,
    @SerialName("LOVE") Love,
    @SerialName("SADNESS") Sadness,
    @SerialName("SURPRISE") Surprise,
    @SerialName("UNSPECIFIABLE") Unspecifiable,
}
