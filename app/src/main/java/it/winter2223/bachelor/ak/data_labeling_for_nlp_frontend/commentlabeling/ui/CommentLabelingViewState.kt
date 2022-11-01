package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.commentlabeling.ui

import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.commentlabeling.domain.model.Comment

sealed class CommentLabelingViewState {
    object Loading : CommentLabelingViewState()

    data class Active(
        val commentSet: Set<Comment>
    ) : CommentLabelingViewState()
}
