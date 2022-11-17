package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.commentlabeling.ui

import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.commentlabeling.data.model.Comment

sealed class CommentLabelingViewState {

    val type: CommentLabelingScreenType
        get() {
            return when (this) {
                is Loading -> CommentLabelingScreenType.Loading
                is Active -> CommentLabelingScreenType.Active
            }
        }

    object Loading : CommentLabelingViewState()

    data class Active(
        val comments: List<Comment>,
        val currentCommentIndex: Int,
    ) : CommentLabelingViewState() {
        val currentComment get() = comments[currentCommentIndex]
        val progress get() = currentCommentIndex.toFloat() / comments.size
    }
}

enum class CommentLabelingScreenType {
    Loading,
    Active,
}
