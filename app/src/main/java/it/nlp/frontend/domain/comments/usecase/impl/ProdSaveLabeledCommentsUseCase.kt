package it.nlp.frontend.domain.comments.usecase.impl

import it.nlp.frontend.data.remote.comment.emotionassignment.model.dto.CommentEmotionAssignmentInput
import it.nlp.frontend.data.remote.comment.emotionassignment.model.exception.CommentEmotionAssignmentException
import it.nlp.frontend.data.remote.comment.emotionassignment.repository.CommentEmotionAssignmentRepository
import it.nlp.frontend.data.remote.model.exception.ApiException
import it.nlp.frontend.data.remote.model.exception.NetworkException
import it.nlp.frontend.data.remote.model.exception.ServiceUnavailableException
import it.nlp.frontend.data.remote.model.exception.UnauthorizedException
import it.nlp.frontend.domain.comments.model.Comment
import it.nlp.frontend.domain.comments.model.SaveLabeledCommentsResult
import it.nlp.frontend.domain.comments.usecase.SaveLabeledCommentsUseCase
import it.nlp.frontend.domain.token.model.GetTokenFlowResult
import it.nlp.frontend.domain.token.usecase.GetTokenFlowUseCase
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ProdSaveLabeledCommentsUseCase @Inject constructor(
    private val commentEmotionAssignmentRepository: CommentEmotionAssignmentRepository,
    private val getTokenFlowUseCase: GetTokenFlowUseCase,
) : SaveLabeledCommentsUseCase {
    override suspend fun invoke(comments: List<Comment>): SaveLabeledCommentsResult {
        return try {
            comments.forEach { comment -> requireNotNull(comment.emotion) }
            when (val tokenFlowResult = getTokenFlowUseCase()) {
                is GetTokenFlowResult.Success -> {
                    val token = tokenFlowResult.tokenFlow.first()
                    token?.userId?.let { userId ->
                        val inputs = comments.map {
                            CommentEmotionAssignmentInput(
                                userId = userId,
                                commentId = it.id,
                                emotion = it.emotion!!.toUppercaseString(),
                            )
                        }

                        val postedAssignmentsResult =
                            commentEmotionAssignmentRepository.postCommentEmotionAssignments(inputs)

                        postedAssignmentsResult.fold(
                            onSuccess = {
                                SaveLabeledCommentsResult.Success
                            },
                            onFailure = { apiException ->
                                saveLabeledCommentsResultForApiException(apiException)
                            },
                        )

                    } ?: SaveLabeledCommentsResult.Failure.NoToken
                }
                is GetTokenFlowResult.Failure -> {
                    SaveLabeledCommentsResult.Failure.ReadingToken
                }
            }
        } catch (e: IllegalArgumentException) {
            SaveLabeledCommentsResult.Failure.NonLabeledComments(e)
        }
    }

    private fun saveLabeledCommentsResultForApiException(apiException: ApiException): SaveLabeledCommentsResult {
        return when (apiException) {
            is ServiceUnavailableException -> SaveLabeledCommentsResult.Failure.ServiceUnavailable
            is NetworkException -> SaveLabeledCommentsResult.Failure.Network
            is UnauthorizedException -> SaveLabeledCommentsResult.Failure.UnauthorizedUser
            is CommentEmotionAssignmentException.WrongEmotion -> SaveLabeledCommentsResult.Failure.WrongEmotionParsing
            is CommentEmotionAssignmentException.AssignmentAlreadyExists ->
                SaveLabeledCommentsResult.Failure.AlreadyAssignedByThisUser
            else -> SaveLabeledCommentsResult.Failure.Unknown
        }
    }
}
