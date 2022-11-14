package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.commentlabeling.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
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
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.R
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.commentlabeling.domain.model.Emotion
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.core.ui.bigPadding

@Composable
fun ProgressSection(
    modifier: Modifier = Modifier,
    currentCommentIndex: Int,
    currentCommentEmotion: Emotion?,
    progress: Float,
    onPreviousButtonClicked: () -> Unit,
    onNextButtonClicked: () -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row {
            PreviousCommentButton(
                onPreviousButtonClicked = onPreviousButtonClicked,
                commentIndex = currentCommentIndex,
            )

            Spacer(modifier = Modifier.width(bigPadding))

            NextCommentButton(
                onNextButtonClicked = onNextButtonClicked,
                emotion = currentCommentEmotion,
            )
        }

        Spacer(modifier = Modifier.height(bigPadding))

        AnimatedLinearProgressIndicator(progress = progress)
    }
}

@Composable
fun PreviousCommentButton(
    modifier: Modifier = Modifier,
    onPreviousButtonClicked: () -> Unit,
    commentIndex: Int,
) {
    OutlinedButton(
        modifier = modifier,
        onClick = onPreviousButtonClicked,
        enabled = commentIndex != 0,
    ) {
        Text(text = stringResource(R.string.previousButtonText))
    }
}

@Composable
fun NextCommentButton(
    modifier: Modifier = Modifier,
    onNextButtonClicked: () -> Unit,
    emotion: Emotion?,
) {
    Button(
        modifier = modifier,
        onClick = onNextButtonClicked,
        enabled = emotion != null,
    ) {
        Text(text = stringResource(R.string.nextButtonText))
    }
}

@Composable
fun AnimatedLinearProgressIndicator(
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
