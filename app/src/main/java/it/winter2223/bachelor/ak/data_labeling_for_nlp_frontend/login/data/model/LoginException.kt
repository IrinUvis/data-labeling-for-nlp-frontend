package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.data.model

sealed class LoginException : Throwable() {
    class EmptyCredentialsException : LoginException()

    class InvalidCredentialsException : LoginException()
}