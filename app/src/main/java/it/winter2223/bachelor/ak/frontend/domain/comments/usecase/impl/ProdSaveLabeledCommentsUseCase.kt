package it.winter2223.bachelor.ak.frontend.domain.comments.usecase.impl

import it.winter2223.bachelor.ak.frontend.data.comments.model.dto.CommentEmotionAssignmentInput
import it.winter2223.bachelor.ak.frontend.data.comments.repository.EmotionAssignmentRepository
import it.winter2223.bachelor.ak.frontend.domain.comments.model.Comment
import it.winter2223.bachelor.ak.frontend.domain.comments.model.Emotion
import it.winter2223.bachelor.ak.frontend.domain.comments.model.SaveLabeledCommentsResult
import it.winter2223.bachelor.ak.frontend.domain.comments.usecase.SaveLabeledCommentsUseCase
import javax.inject.Inject

class ProdSaveLabeledCommentsUseCase @Inject constructor(
    private val emotionAssignmentRepository: EmotionAssignmentRepository,
) : SaveLabeledCommentsUseCase {
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
            SaveLabeledCommentsResult.Failure.NonLabeledComments
        }
    }
}

private fun Emotion.toUppercaseString() = this.name.uppercase()