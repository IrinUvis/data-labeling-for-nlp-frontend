package it.winter2223.bachelor.ak.frontend.ui.commentlabeling

import android.widget.Toast
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import it.winter2223.bachelor.ak.frontend.R
import it.winter2223.bachelor.ak.frontend.ui.commentlabeling.component.CommentLabelingTopBar
import it.winter2223.bachelor.ak.frontend.ui.commentlabeling.model.UiEmotion
import it.winter2223.bachelor.ak.frontend.ui.core.helpers.getString

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentLabelingContent(
    viewState: CommentLabelingViewState,
    onEmotionSelected: (UiEmotion) -> Unit,
    onPreviousButtonClicked: () -> Unit,
    onNextButtonClicked: () -> Unit,
    onBackButtonClicked: () -> Unit,
    onGoToNextComment: () -> Unit,
    onCloseDialog: () -> Unit,
) {
    Scaffold(
        topBar = { CommentLabelingTopBar(onBackButtonClicked = onBackButtonClicked) }
    ) { paddingValues ->
        Crossfade(
            modifier = Modifier.padding(paddingValues),
            targetState = viewState.type
        ) { screenType ->
            val context = LocalContext.current

            when (screenType) {
                CommentLabelingScreenType.Loading -> {
                    (viewState as? CommentLabelingViewState.Loading)?.let { state ->
                        LoadingCommentLabelingContent(
                            text = state.text.getString(),
                        )
                    }
                }
                CommentLabelingScreenType.CommentLoadingError -> {
                    LaunchedEffect(viewState) {
                        Toast.makeText(
                            context,
                            R.string.cannotLoadCommentsErrorMessage,
                            Toast.LENGTH_SHORT,
                        ).show()
                        onBackButtonClicked()
                    }

                    LoadingCommentLabelingContent(
                        text = stringResource(id = R.string.loadingComments)
                    )
                }
                CommentLabelingScreenType.Loaded -> {
                    (viewState as? CommentLabelingViewState.Loaded)?.let { state ->

                        LaunchedEffect(viewState) {
                            if (state is CommentLabelingViewState.Loaded.CommentPostingError) {
                                Toast.makeText(
                                    context,
                                    state.errorMessage.getString(context),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        ActiveCommentLabelingContent(
                            viewState = state,
                            onEmotionSelected = onEmotionSelected,
                            onPreviousButtonClicked = onPreviousButtonClicked,
                            onNextButtonClicked = onNextButtonClicked,
                            onGoToNextComment = onGoToNextComment,
                            onCloseDialog = onCloseDialog,
                        )
                    }
                }
            }
        }
    }
}



