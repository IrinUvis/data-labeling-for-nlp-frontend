package it.winter2223.bachelor.ak.frontend.ui.commentlabeling

import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import it.winter2223.bachelor.ak.frontend.data.comments.model.Emotion

@Composable
fun CommentLabelingContent(
    viewState: CommentLabelingViewState,
    onEmotionSelected: (Emotion) -> Unit,
    onPreviousButtonClicked: () -> Unit,
    onNextButtonClicked: () -> Unit,
    onBackButtonClicked: () -> Unit,
    onSettingsButtonClicked: () -> Unit,
) {
    Crossfade(targetState = viewState.type) { screenType ->
        when (screenType) {
            CommentLabelingScreenType.Loading -> {
                LoadingCommentLabelingContent()
            }
            CommentLabelingScreenType.Active -> {
                (viewState as? CommentLabelingViewState.Active)?.let { state ->
                    ActiveCommentLabelingContent(
                        viewState = state,
                        onEmotionSelected = onEmotionSelected,
                        onPreviousButtonClicked = onPreviousButtonClicked,
                        onNextButtonClicked = onNextButtonClicked,
                        onBackButtonClicked = onBackButtonClicked,
                        onSettingButtonClicked = onSettingsButtonClicked,
                    )
                }
            }
        }
    }
}



