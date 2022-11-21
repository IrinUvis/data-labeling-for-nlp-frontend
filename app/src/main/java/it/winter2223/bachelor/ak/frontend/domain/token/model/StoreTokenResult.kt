package it.winter2223.bachelor.ak.frontend.domain.token.model

sealed class StoreTokenResult {
    object Success : StoreTokenResult()

    object Failure : StoreTokenResult()
}
