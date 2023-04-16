package it.nlp.frontend.ui.login

import it.nlp.frontend.ui.core.helpers.UiText
import it.nlp.frontend.ui.login.model.UiCredentials


sealed class LogInViewState(
    open val credentials: UiCredentials,
    open val inputsEnabled: Boolean = true,
) {
    object Initial : LogInViewState(credentials = UiCredentials())

    data class Active(
        override val credentials: UiCredentials,
        val emailInputErrorMessage: UiText? = null,
        val passwordInputErrorMessage: UiText? = null,
    ) : LogInViewState(credentials = credentials)

    sealed class Submitting(override val credentials: UiCredentials) :
        LogInViewState(
            credentials = credentials,
            inputsEnabled = false,
        ) {
        data class LogIn(override val credentials: UiCredentials) :
            Submitting(credentials = credentials)

        data class SignUp(override val credentials: UiCredentials) :
            Submitting(credentials = credentials)
    }

    data class SubmissionError(
        override val credentials: UiCredentials,
        val errorMessage: UiText,
    ) : LogInViewState(credentials = credentials)

    object Completed : LogInViewState(
        credentials = UiCredentials(),
        inputsEnabled = false,
    )
}
