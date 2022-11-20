package it.winter2223.bachelor.ak.frontend.domain.token.model

sealed class RefreshTokenResult {
    object Success : RefreshTokenResult()

    sealed class Failure : RefreshTokenResult() {
        object DataStore : Failure()

        object Network : Failure()
    }
}
