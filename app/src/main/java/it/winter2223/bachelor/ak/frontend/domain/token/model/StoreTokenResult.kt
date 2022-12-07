package it.winter2223.bachelor.ak.frontend.domain.token.model

sealed class StoreTokenResult {
    object Success : StoreTokenResult()

    data class Failure(val e: Exception) : StoreTokenResult()
}
