package it.winter2223.bachelor.ak.frontend.data.comments.repository.impl

import it.winter2223.bachelor.ak.frontend.data.comments.model.dto.CommentEmotionAssignmentInput
import it.winter2223.bachelor.ak.frontend.data.comments.model.dto.CommentEmotionAssignmentOutput
import it.winter2223.bachelor.ak.frontend.data.comments.model.dto.EmotionDto
import it.winter2223.bachelor.ak.frontend.data.comments.repository.EmotionAssignmentRepository
import kotlinx.coroutines.delay
import java.util.UUID
import javax.inject.Inject

class NetworkEmotionAssignmentRepository @Inject constructor() : EmotionAssignmentRepository {
    override suspend fun postCommentEmotionAssignment(
        commentEmotionAssignmentInputs: List<CommentEmotionAssignmentInput>,
    ): Result<List<CommentEmotionAssignmentOutput>> {
        @Suppress("MagicNumber")
        delay(2000)
        return Result.success(
            List(
                @Suppress("MagicNumber") 10
            ) {
                CommentEmotionAssignmentOutput(
                    assignmentId = UUID.randomUUID(),
                    userId = "hello",
                    commentId = "hello",
                    emotionDto = EmotionDto.Anger
                )
            }
        )
    }
}
