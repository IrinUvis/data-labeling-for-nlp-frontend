package it.winter2223.bachelor.ak.frontend.ui.commentlabeling

import it.winter2223.bachelor.ak.frontend.ui.commentlabeling.model.UiComment
import it.winter2223.bachelor.ak.frontend.ui.core.helpers.UiText

sealed class CommentLabelingViewState {

    val type: CommentLabelingScreenType
        get() {
            return when (this) {
                is Loading -> CommentLabelingScreenType.Loading
                is CommentLoadingError -> CommentLabelingScreenType.CommentLoadingError
                is Loaded -> CommentLabelingScreenType.Loaded
            }
        }

    data class Loading(
        val text: UiText,
    ) : CommentLabelingViewState()

    object CommentLoadingError : CommentLabelingViewState()

    sealed class Loaded(
        open val comments: List<UiComment>,
        open val currentCommentIndex: Int,
    ) : CommentLabelingViewState() {
        val currentComment get() = comments[currentCommentIndex]
        val progress get() = currentCommentIndex.toFloat() / comments.size

        data class CommentPostingError(
            override val comments: List<UiComment>,
            override val currentCommentIndex: Int,
            val errorMessage: UiText,
        ) : Loaded(
            comments = comments,
            currentCommentIndex = currentCommentIndex,
        )

        data class GoToNextWithUnspecifiableRequested(
            override val comments: List<UiComment>,
            override val currentCommentIndex: Int,
        ) : Loaded(
            comments = comments,
            currentCommentIndex = currentCommentIndex,
        )

        data class Active(
            override val comments: List<UiComment>,
            override val currentCommentIndex: Int,
        ) : Loaded(
            comments = comments,
            currentCommentIndex = currentCommentIndex,
        )
    }
}

enum class CommentLabelingScreenType {
    Loading,
    CommentLoadingError,
    Loaded,
}
