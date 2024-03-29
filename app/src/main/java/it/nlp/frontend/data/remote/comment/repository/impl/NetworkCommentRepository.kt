package it.nlp.frontend.data.remote.comment.repository.impl

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.HttpStatusCode
import io.ktor.utils.io.errors.IOException
import it.nlp.frontend.data.remote.model.exception.ApiException
import it.nlp.frontend.data.core.model.DataResult
import it.nlp.frontend.data.remote.comment.model.dto.CommentOutput
import it.nlp.frontend.data.remote.comment.model.exception.toCommentException
import it.nlp.frontend.data.remote.comment.repository.CommentRepository
import it.nlp.frontend.data.remote.model.exception.NetworkException
import it.nlp.frontend.data.remote.model.exception.ServiceUnavailableException
import it.nlp.frontend.data.remote.model.exception.UnauthorizedException
import it.nlp.frontend.di.BASE_URL
import javax.inject.Inject

class NetworkCommentRepository @Inject constructor(
    private val httpClient: HttpClient,
) : CommentRepository {
    companion object {
        private const val TAG = "NetworkCommentRepo"
        private const val URL = "$BASE_URL/comment"
    }

    override suspend fun fetchComments(
        userId: String,
        commentsNumber: Int,
    ): DataResult<List<CommentOutput>, ApiException> {
        return try {
            val response = httpClient.get(URL) {
                parameter("userId", userId)
                parameter("commentsNumber", commentsNumber.toString())
            }
            val commentOutputs = response.body<List<CommentOutput>>()
            DataResult.Success(commentOutputs)
        } catch (e: ResponseException) {
            Log.e(TAG, "fetchComments: response status is ${e.response.status}", e)
            when (e.response.status) {
                HttpStatusCode.Unauthorized,
                HttpStatusCode.GatewayTimeout,
                -> DataResult.Failure(
                    UnauthorizedException(
                        e.message,
                        e.cause,
                    ),
                )
                HttpStatusCode.ServiceUnavailable -> DataResult.Failure(
                    ServiceUnavailableException(
                        e.message,
                        e.cause
                    )
                )
                else -> DataResult.Failure(e.toCommentException())
            }
        } catch (e: IOException) {
            Log.e(TAG, "fetchComments: No network", e)
            DataResult.Failure(NetworkException(e.message, e.cause))
        }
    }
}
