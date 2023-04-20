package it.nlp.frontend.ui.textslabeling

import android.widget.Toast
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import it.nlp.frontend.ui.textslabeling.component.TextsLabelingTopBar
import it.nlp.frontend.ui.textslabeling.model.UiEmotion
import it.nlp.frontend.ui.core.helpers.getString
import it.nlp.frontend.ui.core.helpers.mediumPadding

@Suppress("LongMethod")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextsLabelingContent(
    viewState: TextsLabelingViewState,
    onEmotionSelected: (UiEmotion) -> Unit,
    onPreviousButtonClicked: () -> Unit,
    onNextButtonClicked: () -> Unit,
    onBackButtonClicked: () -> Unit,
    onGoToNextText: () -> Unit,
    onCloseDialog: () -> Unit,
    onRetryLoading: () -> Unit,
    onNavigateToLogIn: () -> Unit,
) {
    Scaffold(
        topBar = { TextsLabelingTopBar(onBackButtonClicked = onBackButtonClicked) }
    ) { paddingValues ->
        Crossfade(
            modifier = Modifier.padding(paddingValues),
            targetState = viewState.type
        ) { screenType ->
            val context = LocalContext.current

            when (screenType) {
                TextsLabelingScreenType.Loading -> {
                    (viewState as? TextsLabelingViewState.Loading)?.let { state ->
                        LoadingTextsLabelingContent(
                            modifier = Modifier.padding(mediumPadding),
                            text = state.text.getString(),
                        )
                    }
                }
                TextsLabelingScreenType.AuthError -> {
                    LaunchedEffect(viewState) {
                        if (viewState is TextsLabelingViewState.AuthError) {
                            Toast.makeText(
                                context,
                                viewState.errorMessage.getString(context),
                                Toast.LENGTH_SHORT,
                            ).show()
                            onNavigateToLogIn()
                        }
                    }
                }
                TextsLabelingScreenType.TextsLoadingError -> {
                    (viewState as? TextsLabelingViewState.TextsLoadingError)?.let { state ->
                        LoadingErrorTextsLabelingContent(
                            modifier = Modifier.padding(mediumPadding),
                            errorMessage = state.errorMessage.getString(),
                            onRetryLoading = onRetryLoading,
                        )
                    }
                }
                TextsLabelingScreenType.Loaded -> {
                    (viewState as? TextsLabelingViewState.Loaded)?.let { state ->
                        LaunchedEffect(viewState) {
                            if (state is TextsLabelingViewState.Loaded.TextsPostingError) {
                                Toast.makeText(
                                    context,
                                    state.errorMessage.getString(context),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        TextsLabelingActiveContent(
                            viewState = state,
                            onEmotionSelected = onEmotionSelected,
                            onPreviousButtonClicked = onPreviousButtonClicked,
                            onNextButtonClicked = onNextButtonClicked,
                            onGoToNextText = onGoToNextText,
                            onCloseDialog = onCloseDialog,
                        )
                    }
                }
            }
        }
    }
}



