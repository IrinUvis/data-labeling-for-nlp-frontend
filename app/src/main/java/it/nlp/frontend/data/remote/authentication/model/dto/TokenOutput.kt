package it.nlp.frontend.data.remote.authentication.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokenOutput(
    @SerialName("value")
    val value: String,

    @SerialName("expiresIn")
    val expiresIn: Long,
)
