package it.winter2223.bachelor.ak.frontend.data.comments.repository

import it.winter2223.bachelor.ak.frontend.data.comments.model.Comment

interface CommentRepository {
    suspend fun fetchComments(quantity: Int): Result<List<Comment>>

    suspend fun postComments(comments: List<Comment>): Result<Unit>
}
