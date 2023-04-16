package it.nlp.frontend.ui.login.model

data class UiCredentials(
    val email: String = "",
    val password: String = "",
) {
    fun withUpdatedEmail(newEmail: String) = UiCredentials(email = newEmail, password = password)

    fun withUpdatedPassword(newPassword: String) =
        UiCredentials(email = email, password = newPassword)
}
