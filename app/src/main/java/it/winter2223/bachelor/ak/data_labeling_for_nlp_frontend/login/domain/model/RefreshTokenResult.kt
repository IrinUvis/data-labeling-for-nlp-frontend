package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.domain.model

sealed class RefreshTokenResult {
    object Success : RefreshTokenResult()

    sealed class Failure : RefreshTokenResult() {
        object DataStore : Failure()

        object Network : Failure()
    }
}
