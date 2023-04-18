package it.nlp.frontend.data.remote.authentication.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenOutput(
    @SerialName("userId")
    val userId: String,

    @SerialName("accessTokenOutput")
    val accessTokenOutput: TokenOutput,

    @SerialName("refreshTokenOutput")
    val refreshTokenOutput: TokenOutput,
)
