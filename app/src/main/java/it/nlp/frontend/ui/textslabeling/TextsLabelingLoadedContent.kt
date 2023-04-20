package it.nlp.frontend.ui.textslabeling

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import it.nlp.frontend.ui.textslabeling.component.TextCard
import it.nlp.frontend.ui.textslabeling.component.EmotionSelector
import it.nlp.frontend.ui.textslabeling.component.ProgressSection
import it.nlp.frontend.ui.textslabeling.component.UnspecifiableEmotionDialog
import it.nlp.frontend.ui.textslabeling.model.UiEmotion
import it.nlp.frontend.ui.core.component.VerticalSpacer
import it.nlp.frontend.ui.core.helpers.bigPadding
import it.nlp.frontend.ui.core.helpers.getString
import it.nlp.frontend.ui.core.helpers.mediumPadding
import it.nlp.frontend.ui.core.helpers.smallPadding

@Composable
fun TextsLabelingActiveContent(
    modifier: Modifier = Modifier,
    viewState: TextsLabelingViewState.Loaded,
    onEmotionSelected: (UiEmotion) -> Unit,
    onPreviousButtonClicked: () -> Unit,
    onNextButtonClicked: () -> Unit,
    onGoToNextText: () -> Unit,
    onCloseDialog: () -> Unit,
) {
    BackHandler(
        enabled = viewState.currentTextIndex != 0,
        onBack = onPreviousButtonClicked,
    )

    if (viewState is TextsLabelingViewState.Loaded.GoToNextWithUnspecifiableRequested) {
        UnspecifiableEmotionDialog(
            onCloseDialog = onCloseDialog,
            onGoToNextText = onGoToNextText,
        )
    }

    Column(
        modifier = modifier
            .padding(
                start = mediumPadding,
                end = mediumPadding,
                top = smallPadding,
                bottom = bigPadding,
            )
            .fillMaxSize()
    ) {
        TextCard(
            modifier = Modifier.weight(1f),
            scrollState = rememberScrollState(),
            text = viewState.currentText.text.getString(),
        )

        VerticalSpacer(height = mediumPadding)

        EmotionSelector(
            modifier = Modifier
                .weight(2f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            selectedEmotion = viewState.currentText.emotion,
            onEmotionSelected = onEmotionSelected,
        )

        VerticalSpacer(height = mediumPadding)

        ProgressSection(
            modifier = Modifier.fillMaxWidth(),
            currentTextIndex = viewState.currentTextIndex,
            currentTextEmotion = viewState.currentText.emotion,
            progress = viewState.progress,
            onPreviousButtonClicked = onPreviousButtonClicked,
            onNextButtonClicked = onNextButtonClicked,
        )
    }
}
