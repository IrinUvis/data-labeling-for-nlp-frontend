package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.data.model

sealed class LogInException : Throwable() {
    class EmptyCredentialsException : LogInException()

    class InvalidCredentialsException : LogInException()
}
