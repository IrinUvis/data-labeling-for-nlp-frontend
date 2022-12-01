package it.winter2223.bachelor.ak.frontend.domain.comments.usecase.impl

import android.util.Log
import it.winter2223.bachelor.ak.frontend.data.remote.comment.emotionassignment.model.dto.CommentEmotionAssignmentInput
import it.winter2223.bachelor.ak.frontend.data.remote.comment.emotionassignment.model.exception.CommentEmotionAssignmentException
import it.winter2223.bachelor.ak.frontend.data.remote.comment.emotionassignment.repository.CommentEmotionAssignmentRepository
import it.winter2223.bachelor.ak.frontend.data.remote.model.exception.NetworkException
import it.winter2223.bachelor.ak.frontend.domain.comments.model.Comment
import it.winter2223.bachelor.ak.frontend.domain.comments.model.Emotion
import it.winter2223.bachelor.ak.frontend.domain.comments.model.SaveLabeledCommentsResult
import it.winter2223.bachelor.ak.frontend.domain.comments.usecase.SaveLabeledCommentsUseCase
import javax.inject.Inject

class ProdSaveLabeledCommentsUseCase @Inject constructor(
    private val commentEmotionAssignmentRepository: CommentEmotionAssignmentRepository,
) : SaveLabeledCommentsUseCase {
    companion object {
        private const val TAG = "ProdSaveLabeledCommentsUC"
    }

    override suspend fun invoke(comments: List<Comment>): SaveLabeledCommentsResult {
        return try {
            comments.forEach { comment -> requireNotNull(comment.emotion) }
            val inputs = comments.map {
                CommentEmotionAssignmentInput(
                    userId = "whatever",
                    commentId = "whatever",
                    emotion = it.emotion!!.toUppercaseString(),
                )
            }
            val postedComments = commentEmotionAssignmentRepository.postCommentEmotionAssignment(inputs)
            postedComments.fold(
                onSuccess = {
                    SaveLabeledCommentsResult.Success
                },
                onFailure = { apiException ->
                    when(apiException) {
                        is NetworkException -> {
                            SaveLabeledCommentsResult.Failure.Unknown
                        }
                        is CommentEmotionAssignmentException -> {
                            SaveLabeledCommentsResult.Failure.Unknown
                        }
                        else -> {
                            SaveLabeledCommentsResult.Failure.Unknown
                        }
                    }
                },
            )
        } catch (e: IllegalArgumentException) {
            Log.e(TAG, e.message, e)
            SaveLabeledCommentsResult.Failure.NonLabeledComments
        }
    }
}

private fun Emotion.toUppercaseString() = this.name.uppercase()
