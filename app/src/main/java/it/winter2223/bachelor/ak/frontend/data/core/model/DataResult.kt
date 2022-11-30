package it.winter2223.bachelor.ak.frontend.data.core.model

sealed class DataResult<out T, out E : Throwable> {
    data class Success<out T>(val value: T) : DataResult<T, Nothing>()

    data class Failure<out E : Throwable>(val throwable: E) : DataResult<Nothing, E>()

    inline fun <R> fold(
        onSuccess: (T) -> R,
        onFailure: (E) -> R,
    ): R {

        return when (this) {
            is Success -> onSuccess(this.value)
            is Failure -> onFailure(this.throwable)
        }
    }
}
