package it.nlp.frontend.ui.textslabeling.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import it.nlp.frontend.R
import it.nlp.frontend.ui.textslabeling.model.UiEmotion
import it.nlp.frontend.ui.core.component.HorizontalSpacer
import it.nlp.frontend.ui.core.component.VerticalSpacer
import it.nlp.frontend.ui.core.helpers.bigPadding
import it.nlp.frontend.ui.core.helpers.mediumPadding

@Composable
fun ProgressSection(
    modifier: Modifier = Modifier,
    currentTextIndex: Int,
    currentTextEmotion: UiEmotion?,
    progress: Float,
    onPreviousButtonClicked: () -> Unit,
    onNextButtonClicked: () -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row {
            PreviousTextButton(
                onPreviousButtonClicked = onPreviousButtonClicked,
                currentTextIndex = currentTextIndex,
            )

            HorizontalSpacer(width = bigPadding)

            NextTextButton(
                onNextButtonClicked = onNextButtonClicked,
                emotion = currentTextEmotion,
            )
        }

        VerticalSpacer(height = mediumPadding)

        AnimatedLinearProgressIndicator(progress = progress)
    }
}

@Composable
private fun PreviousTextButton(
    modifier: Modifier = Modifier,
    onPreviousButtonClicked: () -> Unit,
    currentTextIndex: Int,
) {
    OutlinedButton(
        modifier = modifier,
        onClick = onPreviousButtonClicked,
        enabled = currentTextIndex != 0,
    ) {
        Text(
            text = stringResource(R.string.previousButtonText),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
private fun NextTextButton(
    modifier: Modifier = Modifier,
    onNextButtonClicked: () -> Unit,
    emotion: UiEmotion?,
) {
    Button(
        modifier = modifier,
        onClick = onNextButtonClicked,
        enabled = emotion != null,
    ) {
        Text(
            text = stringResource(R.string.nextButtonText),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
private fun AnimatedLinearProgressIndicator(
    modifier: Modifier = Modifier,
    progress: Float,
) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec,
    )
    LinearProgressIndicator(
        modifier = modifier,
        progress = animatedProgress,
    )
}
