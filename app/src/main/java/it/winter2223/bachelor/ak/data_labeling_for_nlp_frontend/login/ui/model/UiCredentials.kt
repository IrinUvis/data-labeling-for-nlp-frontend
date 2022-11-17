package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.ui.model

data class UiCredentials(
    val email: String = "",
    val password: String = "",
) {
    fun withUpdatedEmail(newEmail: String) = UiCredentials(email = newEmail, password = password)

    fun withUpdatedPassword(newPassword: String) =
        UiCredentials(email = email, password = newPassword)
}
