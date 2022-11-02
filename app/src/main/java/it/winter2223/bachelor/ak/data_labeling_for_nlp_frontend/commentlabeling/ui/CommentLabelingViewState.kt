package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.commentlabeling.ui

import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.commentlabeling.domain.model.Comment

data class CommentLabelingViewState(
    val screenType: CommentLabelingScreenType = CommentLabelingScreenType.Loading,
    val activeLabelingScreenData: ActiveLabelingScreenData? = null,
)

data class ActiveLabelingScreenData(
    val comments: List<Comment>,
    val currentCommentIndex: Int,
) {
    val currentComment get() = comments[currentCommentIndex]
    val progress get() = currentCommentIndex.toFloat() / comments.size
}

enum class CommentLabelingScreenType {
    Loading,
    Active,
}
