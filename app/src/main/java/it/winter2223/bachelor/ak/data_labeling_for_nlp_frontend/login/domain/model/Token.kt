package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.domain.model

data class Token(
    val authToken: String,
    val refreshToken: String,
)
