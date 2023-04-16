package it.nlp.frontend.data.remote.authentication.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserInput(
    @SerialName("email")
    val email: String,

    @SerialName("password")
    val password: String,
)
