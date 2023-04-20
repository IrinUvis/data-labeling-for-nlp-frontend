package it.nlp.frontend.data.remote.emotion.assignments.repository.impl

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.utils.io.errors.IOException
import it.nlp.frontend.data.core.model.DataResult
import it.nlp.frontend.data.remote.emotion.assignments.model.dto.TextEmotionAssignmentInput
import it.nlp.frontend.data.remote.emotion.assignments.model.dto.TextEmotionAssignmentNumberOutput
import it.nlp.frontend.data.remote.emotion.assignments.model.dto.TextEmotionAssignmentOutput
import it.nlp.frontend.data.remote.emotion.assignments.model.exception.toTextEmotionAssignmentException
import it.nlp.frontend.data.remote.emotion.assignments.repository.TextEmotionAssignmentRepository
import it.nlp.frontend.data.remote.model.exception.ApiException
import it.nlp.frontend.data.remote.model.exception.NetworkException
import it.nlp.frontend.data.remote.model.exception.ServiceUnavailableException
import it.nlp.frontend.data.remote.model.exception.UnauthorizedException
import it.nlp.frontend.di.BASE_URL
import javax.inject.Inject

class NetworkTextEmotionAssignmentRepository @Inject constructor(
    private val httpClient: HttpClient,
) : TextEmotionAssignmentRepository {
    companion object {
        private const val URL = "$BASE_URL/emotion-assignments"
    }

    override suspend fun postTextEmotionAssignments(
        textEmotionAssignmentInputs: List<TextEmotionAssignmentInput>,
    ): DataResult<List<TextEmotionAssignmentOutput>, ApiException> {
        return try {
            val response = httpClient.post(URL) {
                contentType(ContentType.Application.Json)
                setBody(textEmotionAssignmentInputs)
            }
            val textEmotionAssignmentOutputs =
                response.body<List<TextEmotionAssignmentOutput>>()
            DataResult.Success(textEmotionAssignmentOutputs)
        } catch (e: ResponseException) {
            dataResultFailureForResponseException(e)
        } catch (e: IOException) {
            DataResult.Failure(NetworkException(e.message, e.cause))
        }
    }

    override suspend fun getNumberOfTextEmotionAssignments():
            DataResult<TextEmotionAssignmentNumberOutput, ApiException> {
        return try {
            val response = httpClient.get("$URL/count")
            val textEmotionAssignmentNumberOutput =
                response.body<TextEmotionAssignmentNumberOutput>()
            DataResult.Success(textEmotionAssignmentNumberOutput)
        } catch (e: ResponseException) {
            dataResultFailureForResponseException(e)
        } catch (e: IOException) {
            DataResult.Failure(NetworkException(e.message, e.cause))
        }
    }

    private suspend fun dataResultFailureForResponseException(
        exception: ResponseException
    ): DataResult.Failure<ApiException> = when (exception.response.status) {
        HttpStatusCode.Unauthorized -> DataResult.Failure(
            UnauthorizedException(
                exception.message,
                exception.cause,
            ),
        )

        HttpStatusCode.GatewayTimeout,
        HttpStatusCode.ServiceUnavailable -> DataResult.Failure(
            ServiceUnavailableException(
                exception.message,
                exception.cause
            )
        )

        else -> DataResult.Failure(exception.toTextEmotionAssignmentException())
    }
}
