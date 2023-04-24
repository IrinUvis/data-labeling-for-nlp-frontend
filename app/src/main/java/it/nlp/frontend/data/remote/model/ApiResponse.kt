package it.nlp.frontend.data.remote.model

sealed class ApiResponse<out T> {
    data class Success<out T>(
        val data: T
    ) : ApiResponse<T>()

    data class Failure(
        val errorMessage: String,
        val code: Int,
    ) : ApiResponse<Nothing>()

    data class Exception(
        val exception: Throwable
    ) : ApiResponse<Nothing>()
}
