package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.data.model.dto

data class UserOutput(
    val email: String,
    val idToken: String,
    val refreshToken: String,
)
