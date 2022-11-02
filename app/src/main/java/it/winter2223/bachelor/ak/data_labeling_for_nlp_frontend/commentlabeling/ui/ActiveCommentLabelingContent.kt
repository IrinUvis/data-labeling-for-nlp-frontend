@file:OptIn(ExperimentalMaterial3Api::class)

package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.commentlabeling.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.commentlabeling.domain.model.Comment
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.commentlabeling.domain.model.Emotion

@Composable
fun ActiveCommentLabelingContent(
    currentComment: Comment,
    progress: Float,
    onEmotionSelected: (Emotion) -> Unit,
    onNextButtonClicked: () -> Unit,
    onBackButtonClicked: () -> Unit,
    onSettingButtonClicked: () -> Unit,
) {
    Scaffold(
        topBar = {
            CommentLabelingTopBar(
                onBackButtonClicked = onBackButtonClicked,
                onSettingsButtonClicked = onSettingButtonClicked,
            )
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
        ) {
            Column(
                modifier = Modifier.padding(
                    horizontal = 16.dp,
                    vertical = 8.dp,
                )
            ) {
                CommentText(currentComment = currentComment)

                Spacer(modifier = Modifier.height(16.dp))

                for (emotion in Emotion.values()) {
                    EmotionSelectionRadioButton(
                        currentComment = currentComment,
                        emotion = emotion,
                        onEmotionSelected = onEmotionSelected,
                    )

                }

                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    NextCommentButton(
                        onNextButtonClicked = onNextButtonClicked,
                        currentComment = currentComment,
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    AnimatedLinearProgressIndicator(progress = progress)
                }
            }
        }
    }
}

@Composable
private fun CommentText(currentComment: Comment) {
    Text(
        text = "\"${currentComment.text}\"",
        style = MaterialTheme.typography.bodyMedium,
        textAlign = TextAlign.Center,
    )
}

@Composable
private fun NextCommentButton(
    onNextButtonClicked: () -> Unit,
    currentComment: Comment,
) {
    Button(
        onClick = onNextButtonClicked,
        enabled = currentComment.emotion != null,
    ) {
        Text(text = "Next")
    }
}

@Composable
private fun AnimatedLinearProgressIndicator(progress: Float) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec,
    )
    LinearProgressIndicator(progress = animatedProgress)
}

@Composable
private fun EmotionSelectionRadioButton(
    currentComment: Comment,
    emotion: Emotion,
    onEmotionSelected: (Emotion) -> Unit,
) {
    Surface(
        shape = MaterialTheme.shapes.extraLarge,
        color = Color.Transparent,
    ) {
        Row(
            modifier = Modifier
                .selectable(
                    selected = currentComment.emotion == emotion,
                    onClick = { onEmotionSelected(emotion) },
                )
                .padding(
                    end = 24.dp,
                    top = 12.dp,
                    bottom = 12.dp,
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            RadioButton(
                modifier = Modifier.padding(horizontal = 8.dp),
                selected = currentComment.emotion == emotion,
                onClick = null,
            )
            Text(text = emotion.name)
        }
    }
}

@Composable
private fun CommentLabelingTopBar(
    onBackButtonClicked: () -> Unit,
    onSettingsButtonClicked: () -> Unit,
) {
    TopAppBar(
        title = { Text(text = "Emotion assignment") },
        navigationIcon = {
            IconButton(onClick = onBackButtonClicked) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Go to Settings button",
                )
            }
        },
        actions = {
            IconButton(onClick = onSettingsButtonClicked) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Go to Settings button",
                )
            }
        },
    )
}
