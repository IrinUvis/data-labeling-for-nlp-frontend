package it.winter2223.bachelor.ak.frontend.data.remote.comment.repository

import it.winter2223.bachelor.ak.frontend.data.remote.comment.model.dto.CommentOutput

interface CommentRepository {
    suspend fun fetchComments(quantity: Int): Result<List<CommentOutput>>
}
