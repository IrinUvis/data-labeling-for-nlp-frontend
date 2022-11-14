package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.commentlabeling.data.repository

import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.commentlabeling.data.model.Comment

interface CommentRepository {
    suspend fun fetchComments(quantity: Int): Result<List<Comment>>

    suspend fun postComments(comments: List<Comment>): Result<Unit>
}
