package it.winter2223.bachelor.ak.frontend.data.remote.authentication.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenOutput(
    @SerialName("userId")
    val userId: String,

    @SerialName("idToken")
    val authToken: String,

    @SerialName("expiresIn")
    val expiresIn: String,

    @SerialName("refreshToken")
    val refreshToken: String,
)
