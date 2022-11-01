package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.commentlabeling.ui

import androidx.compose.runtime.Composable

@Composable
fun CommentLabelingContent(
    viewState: CommentLabelingViewState,
    onSettingsButtonPressed: () -> Unit,
) {
    when (viewState) {
        is CommentLabelingViewState.Loading -> {
            LoadingCommentLabelingContent()
        }
        is CommentLabelingViewState.Active -> {
            ActiveCommentLabelingContent(
                onSettingsButtonPressed = onSettingsButtonPressed,
            )
        }
    }
}



