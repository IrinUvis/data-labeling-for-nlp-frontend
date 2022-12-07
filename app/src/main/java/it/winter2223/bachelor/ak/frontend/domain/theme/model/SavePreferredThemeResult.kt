package it.winter2223.bachelor.ak.frontend.domain.theme.model

sealed class SavePreferredThemeResult {
    object Success : SavePreferredThemeResult()

    data class Failure(val e: Exception) : SavePreferredThemeResult()
}
