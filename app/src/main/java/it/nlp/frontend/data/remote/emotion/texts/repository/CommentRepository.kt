package it.nlp.frontend.data.remote.emotion.texts.repository

import it.nlp.frontend.data.remote.model.exception.ApiException
import it.nlp.frontend.data.core.model.DataResult
import it.nlp.frontend.data.remote.emotion.texts.model.dto.CommentOutput

interface CommentRepository {
    suspend fun fetchComments(
        userId: String,
        commentsNumber: Int,
    ): DataResult<List<CommentOutput>, ApiException>
}
