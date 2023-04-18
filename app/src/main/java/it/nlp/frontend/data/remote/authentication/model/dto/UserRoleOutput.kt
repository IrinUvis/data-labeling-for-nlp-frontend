package it.nlp.frontend.data.remote.authentication.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class UserRoleOutput {
    @SerialName("USER") User,
    @SerialName("ADMIN") Admin,
}
