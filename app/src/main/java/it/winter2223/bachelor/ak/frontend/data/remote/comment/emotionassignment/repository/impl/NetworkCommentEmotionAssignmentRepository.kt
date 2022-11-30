package it.winter2223.bachelor.ak.frontend.data.remote.comment.emotionassignment.repository.impl

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import it.winter2223.bachelor.ak.frontend.data.core.model.DataResult
import it.winter2223.bachelor.ak.frontend.data.remote.comment.emotionassignment.model.dto.CommentEmotionAssignmentInput
import it.winter2223.bachelor.ak.frontend.data.remote.comment.emotionassignment.model.dto.CommentEmotionAssignmentOutput
import it.winter2223.bachelor.ak.frontend.data.remote.comment.emotionassignment.model.exception.CommentEmotionAssignmentException
import it.winter2223.bachelor.ak.frontend.data.remote.comment.emotionassignment.model.exception.toCommentEmotionAssignmentException
import it.winter2223.bachelor.ak.frontend.data.remote.comment.emotionassignment.repository.CommentEmotionAssignmentRepository
import it.winter2223.bachelor.ak.frontend.di.BASE_URL
import javax.inject.Inject

class NetworkCommentEmotionAssignmentRepository @Inject constructor(
    private val httpClient: HttpClient,
) : CommentEmotionAssignmentRepository {
    companion object {
        private const val TAG = "NetworkEmotionAssignmentRepo"
        private const val URL = "$BASE_URL/assignment"
    }

    override suspend fun postCommentEmotionAssignment(
        commentEmotionAssignmentInputs: List<CommentEmotionAssignmentInput>,
    ): DataResult<List<CommentEmotionAssignmentOutput>, CommentEmotionAssignmentException> {
        return try {
            val response = httpClient.post(URL) {
                setBody(commentEmotionAssignmentInputs)
            }
            val commentEmotionAssignmentOutputs = response.body<List<CommentEmotionAssignmentOutput>>()
            DataResult.Success(commentEmotionAssignmentOutputs)
        } catch (e: ResponseException) {
            Log.e(TAG, "postCommentEmotionAssignment: response status is ${e.response.status}", e)
            DataResult.Failure(e.toCommentEmotionAssignmentException())
        }
    }
}
