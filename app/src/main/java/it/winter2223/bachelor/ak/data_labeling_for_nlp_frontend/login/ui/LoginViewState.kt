package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.ui

import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.core.ui.UiText

sealed class LoginViewState(
    open val credentials: Credentials,
    open val inputsEnabled: Boolean = true,
) {
    object Initial : LoginViewState(credentials = Credentials())

    data class Active(
        override val credentials: Credentials,
        val emailInputErrorMessage: UiText? = null,
        val passwordInputErrorMessage: UiText? = null,
    ) : LoginViewState(credentials = credentials)

    sealed class Submitting(override val credentials: Credentials) :
        LoginViewState(
            credentials = credentials,
            inputsEnabled = false,
        ) {
        data class LogIn(override val credentials: Credentials) :
            Submitting(credentials = credentials)

        data class SignUp(override val credentials: Credentials) :
            Submitting(credentials = credentials)
    }

    data class SubmissionError(
        override val credentials: Credentials,
        val errorMessage: UiText,
    ) : LoginViewState(credentials = credentials)

    object Completed : LoginViewState(
        credentials = Credentials(),
        inputsEnabled = false,
    )
}

data class Credentials(
    val email: String = "",
    val password: String = "",
)

fun Credentials.withUpdatedEmail(newEmail: String) =
    Credentials(email = newEmail, password = password)

fun Credentials.withUpdatedPassword(newPassword: String) =
    Credentials(email = email, password = newPassword)
