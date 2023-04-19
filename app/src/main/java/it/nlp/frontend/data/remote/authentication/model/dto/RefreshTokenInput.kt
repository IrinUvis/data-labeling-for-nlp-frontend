package it.nlp.frontend.data.remote.authentication.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenInput(
    @SerialName("refreshTokenValue")
    val refreshTokenValue: String,
)