package it.winter2223.bachelor.ak.frontend.ui.commentlabeling

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import it.winter2223.bachelor.ak.frontend.data.comments.model.Emotion
import it.winter2223.bachelor.ak.frontend.ui.commentlabeling.component.CommentLabelingTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentLabelingContent(
    viewState: CommentLabelingViewState,
    onEmotionSelected: (Emotion) -> Unit,
    onPreviousButtonClicked: () -> Unit,
    onNextButtonClicked: () -> Unit,
    onBackButtonClicked: () -> Unit,
) {
    Scaffold(
        topBar = { CommentLabelingTopBar(onBackButtonClicked = onBackButtonClicked) }
    ) { paddingValues ->
        Crossfade(
            modifier = Modifier.padding(paddingValues),
            targetState = viewState.type
        ) { screenType ->
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
                        )
                    }
                }
            }
        }
    }
}



