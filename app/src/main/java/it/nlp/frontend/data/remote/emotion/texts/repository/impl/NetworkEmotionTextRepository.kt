package it.nlp.frontend.data.remote.emotion.texts.repository.impl

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.HttpStatusCode
import io.ktor.utils.io.errors.IOException
import it.nlp.frontend.data.remote.model.exception.ApiException
import it.nlp.frontend.data.core.model.DataResult
import it.nlp.frontend.data.remote.emotion.texts.model.dto.EmotionTextOutput
import it.nlp.frontend.data.remote.emotion.texts.model.exception.toEmotionTextException
import it.nlp.frontend.data.remote.emotion.texts.repository.EmotionTextRepository
import it.nlp.frontend.data.remote.model.exception.NetworkException
import it.nlp.frontend.data.remote.model.exception.ServiceUnavailableException
import it.nlp.frontend.data.remote.model.exception.UnauthorizedException
import it.nlp.frontend.di.BASE_URL
import javax.inject.Inject

class NetworkEmotionTextRepository @Inject constructor(
    private val httpClient: HttpClient,
) : EmotionTextRepository {
    companion object {
        private const val URL = "$BASE_URL/emotion-texts"
        private const val EMOTION_TEXTS_NUMBER_PARAMETER = "emotionTextsNumber"
    }

    override suspend fun fetchEmotionTextsToBeAssigned(
        emotionTextsNumber: Int,
    ): DataResult<List<EmotionTextOutput>, ApiException> {
        return try {
            val response = httpClient.get(URL) {
                parameter(EMOTION_TEXTS_NUMBER_PARAMETER, emotionTextsNumber.toString())
            }
            val emotionTextOutputs = response.body<List<EmotionTextOutput>>()
            DataResult.Success(emotionTextOutputs)
        } catch (e: ResponseException) {
            dataResultFailureForResponseException(e)
        } catch (e: IOException) {
            DataResult.Failure(NetworkException(e.message, e.cause))
        }
    }

    private suspend fun dataResultFailureForResponseException(e: ResponseException): DataResult.Failure<ApiException> =
        when (e.response.status) {
            HttpStatusCode.Unauthorized -> DataResult.Failure(
                UnauthorizedException(
                    e.message,
                    e.cause,
                ),
            )

            HttpStatusCode.GatewayTimeout,
            HttpStatusCode.ServiceUnavailable -> DataResult.Failure(
                ServiceUnavailableException(
                    e.message,
                    e.cause
                )
            )

            else -> DataResult.Failure(e.toEmotionTextException())
        }
}
