package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.commentlabeling.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.commentlabeling.domain.model.Comment
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.commentlabeling.domain.model.Emotion
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.core.ui.buttonHorizontalPadding
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.core.ui.buttonVerticalPadding
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.core.ui.smallPadding

@Composable
fun EmotionSelector(
    modifier: Modifier = Modifier,
    currentComment: Comment,
    onEmotionSelected: (Emotion) -> Unit,
) {
    Column(
        modifier = modifier,
    ) {
        for (emotion in Emotion.values()) {
            EmotionSelectionRadioButton(
                comment = currentComment,
                emotion = emotion,
                onEmotionSelected = onEmotionSelected,
            )
        }
    }
}

@Composable
fun EmotionSelectionRadioButton(
    modifier: Modifier = Modifier,
    comment: Comment,
    emotion: Emotion,
    onEmotionSelected: (Emotion) -> Unit,
) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.extraLarge,
        color = Color.Transparent,
    ) {
        Row(
            modifier = Modifier
                .selectable(
                    selected = comment.emotion == emotion,
                    onClick = { onEmotionSelected(emotion) },
                )
                .padding(
                    end = buttonHorizontalPadding,
                    top = buttonVerticalPadding,
                    bottom = buttonVerticalPadding,
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            RadioButton(
                modifier = Modifier.padding(horizontal = smallPadding),
                selected = comment.emotion == emotion,
                onClick = null,
            )
            Text(text = emotion.name)
        }
    }
}
