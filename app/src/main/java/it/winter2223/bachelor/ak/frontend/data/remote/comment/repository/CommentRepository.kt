package it.winter2223.bachelor.ak.frontend.data.remote.comment.repository

import it.winter2223.bachelor.ak.frontend.data.core.model.DataResult
import it.winter2223.bachelor.ak.frontend.data.remote.comment.model.dto.CommentOutput
import it.winter2223.bachelor.ak.frontend.data.remote.comment.model.exception.CommentException

interface CommentRepository {
    suspend fun fetchComments(
        userId: String,
        commentsNumber: Int,
    ): DataResult<List<CommentOutput>, CommentException>
}
