package it.winter2223.bachelor.ak.frontend.domain.comments.usecase.impl

import android.util.Log
import it.winter2223.bachelor.ak.frontend.data.remote.comment.emotionassignment.model.dto.CommentEmotionAssignmentInput
import it.winter2223.bachelor.ak.frontend.data.remote.comment.emotionassignment.repository.EmotionAssignmentRepository
import it.winter2223.bachelor.ak.frontend.domain.comments.model.Comment
import it.winter2223.bachelor.ak.frontend.domain.comments.model.Emotion
import it.winter2223.bachelor.ak.frontend.domain.comments.model.SaveLabeledCommentsResult
import it.winter2223.bachelor.ak.frontend.domain.comments.usecase.SaveLabeledCommentsUseCase
import javax.inject.Inject

class ProdSaveLabeledCommentsUseCase @Inject constructor(
    private val emotionAssignmentRepository: EmotionAssignmentRepository,
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
            val postedComments = emotionAssignmentRepository.postCommentEmotionAssignment(inputs)
            postedComments.fold(
                onSuccess = {
                    SaveLabeledCommentsResult.Success
                },
                onFailure = {
                    SaveLabeledCommentsResult.Failure.Unknown
                },
            )
        } catch (e: IllegalArgumentException) {
            Log.e(TAG, e.message, e)
            SaveLabeledCommentsResult.Failure.NonLabeledComments
        }
    }
}

private fun Emotion.toUppercaseString() = this.name.uppercase()
