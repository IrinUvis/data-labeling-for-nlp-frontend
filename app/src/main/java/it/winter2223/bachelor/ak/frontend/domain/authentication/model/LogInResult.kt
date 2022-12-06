package it.winter2223.bachelor.ak.frontend.domain.authentication.model

sealed class LogInResult {
    object Success : LogInResult()

    sealed class Failure : LogInResult() {

        data class InvalidCredentials(
            val badEmailFormat: Boolean = false,
            val passwordLessThanSixCharacters: Boolean = false,
            val emptyEmail: Boolean = false,
            val emptyPassword: Boolean = false,
        ) : Failure()

        object DataStore : Failure()

        object Unknown : Failure()

        object WrongCredentials : Failure()

        object Network : Failure()

        object ServiceUnavailable : Failure()
    }
}
