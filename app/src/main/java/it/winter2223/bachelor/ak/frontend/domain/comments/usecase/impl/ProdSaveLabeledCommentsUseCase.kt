package it.winter2223.bachelor.ak.frontend.domain.comments.usecase.impl

import android.util.Log
import it.winter2223.bachelor.ak.frontend.data.remote.comment.emotionassignment.model.dto.CommentEmotionAssignmentInput
import it.winter2223.bachelor.ak.frontend.data.remote.comment.emotionassignment.model.exception.CommentEmotionAssignmentException
import it.winter2223.bachelor.ak.frontend.data.remote.comment.emotionassignment.repository.CommentEmotionAssignmentRepository
import it.winter2223.bachelor.ak.frontend.data.remote.model.exception.ApiException
import it.winter2223.bachelor.ak.frontend.data.remote.model.exception.NetworkException
import it.winter2223.bachelor.ak.frontend.data.remote.model.exception.ServiceUnavailableException
import it.winter2223.bachelor.ak.frontend.data.remote.model.exception.UnauthorizedException
import it.winter2223.bachelor.ak.frontend.domain.comments.model.Comment
import it.winter2223.bachelor.ak.frontend.domain.comments.model.Emotion
import it.winter2223.bachelor.ak.frontend.domain.comments.model.SaveLabeledCommentsResult
import it.winter2223.bachelor.ak.frontend.domain.comments.usecase.SaveLabeledCommentsUseCase
import it.winter2223.bachelor.ak.frontend.domain.token.model.GetTokenFlowResult
import it.winter2223.bachelor.ak.frontend.domain.token.usecase.GetTokenFlowUseCase
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ProdSaveLabeledCommentsUseCase @Inject constructor(
    private val commentEmotionAssignmentRepository: CommentEmotionAssignmentRepository,
    private val getTokenFlowUseCase: GetTokenFlowUseCase,
) : SaveLabeledCommentsUseCase {
    companion object {
        private const val TAG = "ProdSaveLabeledCommentsUC"
    }

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
            Log.e(TAG, e.message, e)
            SaveLabeledCommentsResult.Failure.NonLabeledComments
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

private fun Emotion.toUppercaseString() = this.name.uppercase()
