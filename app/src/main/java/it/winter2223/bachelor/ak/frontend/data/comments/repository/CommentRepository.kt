package it.winter2223.bachelor.ak.frontend.data.comments.repository

import it.winter2223.bachelor.ak.frontend.data.comments.model.dto.CommentOutput

interface CommentRepository {
    suspend fun fetchComments(quantity: Int): Result<List<CommentOutput>>
}
