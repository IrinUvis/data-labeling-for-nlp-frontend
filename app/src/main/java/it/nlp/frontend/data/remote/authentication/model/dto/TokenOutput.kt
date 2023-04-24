package it.nlp.frontend.data.remote.authentication.model.dto

data class TokenOutput(
    val value: String,
    val expiresIn: Long,
)
