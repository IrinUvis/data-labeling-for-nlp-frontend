package it.nlp.frontend.domain.emotiontexts.usecase.impl

import it.nlp.frontend.data.remote.emotion.assignments.repository.impl.NetworkTextEmotionAssignmentRepository
import it.nlp.frontend.data.remote.model.ApiResponse
import it.nlp.frontend.data.remote.model.HttpStatus
import it.nlp.frontend.domain.emotiontexts.model.GetNumberOfLabeledTextsResult
import it.nlp.frontend.domain.emotiontexts.usecase.GetNumberOfLabeledTextsUseCase
import java.net.ConnectException
import java.net.SocketTimeoutException
import javax.inject.Inject

class ProdGetNumberOfLabeledTextsUseCase @Inject constructor(
    private val textEmotionAssignmentRepository: NetworkTextEmotionAssignmentRepository
) : GetNumberOfLabeledTextsUseCase {
    override suspend fun invoke(): GetNumberOfLabeledTextsResult {
        return when (val apiResponse = textEmotionAssignmentRepository.getAssignmentsCount()) {
            is ApiResponse.Success -> GetNumberOfLabeledTextsResult.Success(apiResponse.data.assignmentsCount)
            is ApiResponse.Failure -> getNumberOfLabeledTextsResultForApiResponseFailure(apiResponse)
            is ApiResponse.Exception -> getNumberOfLabeledTextsResultForApiResponseException(apiResponse)
        }
    }

    private fun getNumberOfLabeledTextsResultForApiResponseFailure(
        apiResponseFailure: ApiResponse.Failure
    ): GetNumberOfLabeledTextsResult {
        return when (apiResponseFailure.code) {
            HttpStatus.ServiceUnavailable.code, HttpStatus.GatewayTimeout.code
            -> GetNumberOfLabeledTextsResult.Failure.ServiceUnavailable

            HttpStatus.Unauthorized.code -> GetNumberOfLabeledTextsResult.Failure.UnauthorizedUser
            HttpStatus.BadRequest.code -> GetNumberOfLabeledTextsResult.Failure.Unexpected
            else -> GetNumberOfLabeledTextsResult.Failure.Unknown
        }
    }

    private fun getNumberOfLabeledTextsResultForApiResponseException(
        apiResponseException: ApiResponse.Exception
    ): GetNumberOfLabeledTextsResult.Failure {
        return when (apiResponseException.exception) {
            is ConnectException, is SocketTimeoutException -> GetNumberOfLabeledTextsResult.Failure.Network
            else -> GetNumberOfLabeledTextsResult.Failure.Unexpected
        }
    }
}
