package it.nlp.frontend.domain.theme.model

sealed class SavePreferredThemeResult {
    object Success : SavePreferredThemeResult()

    data class Failure(val e: Exception) : SavePreferredThemeResult()
}
