package it.winter2223.bachelor.ak.frontend.domain.comments.model

sealed class GetCommentsToLabelResult {
    data class Success(val comments: List<Comment>) : GetCommentsToLabelResult()

    object Failure : GetCommentsToLabelResult()
}
