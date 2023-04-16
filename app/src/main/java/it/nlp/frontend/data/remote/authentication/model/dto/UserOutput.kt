package it.nlp.frontend.data.remote.authentication.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserOutput(
    @SerialName("email")
    val email: String,

    @SerialName("userId")
    val userId: String,

    @SerialName("idToken")
    val authToken: String,

    @SerialName("expiresIn")
    val expiresIn: String,

    @SerialName("refreshToken")
    val refreshToken: String,
)
