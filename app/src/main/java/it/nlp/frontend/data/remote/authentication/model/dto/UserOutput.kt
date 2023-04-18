package it.nlp.frontend.data.remote.authentication.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserOutput(
    @SerialName("userId")
    val userId: String,

    @SerialName("email")
    val email: String,

    @SerialName("userRoleOutput")
    val userRoleOutput: UserRoleOutput,

    @SerialName("accessTokenOutput")
    val accessTokenOutput: TokenOutput,

    @SerialName("refreshTokenOutput")
    val refreshTokenOutput: TokenOutput,
)
