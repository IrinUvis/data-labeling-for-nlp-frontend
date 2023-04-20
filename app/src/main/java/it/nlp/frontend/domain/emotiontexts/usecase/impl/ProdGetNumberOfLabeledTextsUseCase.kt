package it.nlp.frontend.domain.emotiontexts.usecase.impl

import it.nlp.frontend.data.remote.emotion.assignments.repository.TextEmotionAssignmentRepository
import it.nlp.frontend.data.remote.model.exception.ApiException
import it.nlp.frontend.data.remote.model.exception.NetworkException
import it.nlp.frontend.data.remote.model.exception.ServiceUnavailableException
import it.nlp.frontend.data.remote.model.exception.UnauthorizedException
import it.nlp.frontend.domain.emotiontexts.model.GetNumberOfLabeledTextsResult
import it.nlp.frontend.domain.emotiontexts.usecase.GetNumberOfLabeledTextsUseCase
import javax.inject.Inject

class ProdGetNumberOfLabeledTextsUseCase @Inject constructor(
    private val textEmotionAssignmentRepository: TextEmotionAssignmentRepository
) : GetNumberOfLabeledTextsUseCase {

    override suspend fun invoke(): GetNumberOfLabeledTextsResult {
        val assignmentsNumberResult =
            textEmotionAssignmentRepository.getNumberOfTextEmotionAssignments()

        return assignmentsNumberResult.fold(
            onSuccess = { GetNumberOfLabeledTextsResult.Success(it.assignmentsCount) },
            onFailure = { apiException -> getNumberOfLabeledTextsResultForApiException(apiException) },
        )
    }

    private fun getNumberOfLabeledTextsResultForApiException(
        apiException: ApiException
    ): GetNumberOfLabeledTextsResult {
        return when (apiException) {
            is ServiceUnavailableException -> GetNumberOfLabeledTextsResult.Failure.ServiceUnavailable
            is NetworkException -> GetNumberOfLabeledTextsResult.Failure.Network
            is UnauthorizedException -> GetNumberOfLabeledTextsResult.Failure.UnauthorizedUser
            else -> GetNumberOfLabeledTextsResult.Failure.Unknown
        }
    }
}
