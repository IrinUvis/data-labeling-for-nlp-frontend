package it.nlp.frontend.data.remote.core

import it.nlp.frontend.data.remote.model.ApiResponse
import retrofit2.HttpException
import retrofit2.Response
import java.lang.Exception
import java.net.ConnectException
import javax.inject.Inject

class ApiClient @Inject constructor() {
    @Suppress("TooGenericExceptionCaught")
    suspend fun <T> makeRequest(call: suspend () -> Response<T>): ApiResponse<T> {
        return try {
            val response = call()
            val body = response.body()
            if (response.isSuccessful && body != null) {
                ApiResponse.Success(body)
            } else {
                errorResponseToApiResponseFailure(response)
            }
        } catch (e: HttpException) {
            errorResponseToApiResponseFailure(e.response()!!)
        } catch (e: ConnectException) {
            ApiResponse.Exception(e)
        } catch (e: Exception) {
            ApiResponse.Exception(e)
        }
    }

    private fun errorResponseToApiResponseFailure(response: Response<*>): ApiResponse.Failure {
        val errorMessage = response.errorBody()!!.string()
        val statusCode = response.code()
        return ApiResponse.Failure(errorMessage, statusCode)
    }
}
