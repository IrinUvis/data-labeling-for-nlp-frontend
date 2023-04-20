package it.nlp.frontend.ui.textslabeling.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import it.nlp.frontend.ui.textslabeling.model.UiEmotion
import it.nlp.frontend.ui.core.helpers.buttonHorizontalPadding
import it.nlp.frontend.ui.core.helpers.buttonVerticalPadding
import it.nlp.frontend.ui.core.helpers.smallPadding

@Composable
fun EmotionSelector(
    modifier: Modifier = Modifier,
    selectedEmotion: UiEmotion?,
    onEmotionSelected: (UiEmotion) -> Unit,
) {
    Column(
        modifier = modifier,
    ) {
        for (emotion in UiEmotion.values()) {
            EmotionSelectionRadioButton(
                selectedEmotion = selectedEmotion,
                emotion = emotion,
                onEmotionSelected = onEmotionSelected,
            )
        }
    }
}

@Composable
private fun EmotionSelectionRadioButton(
    modifier: Modifier = Modifier,
    selectedEmotion: UiEmotion?,
    emotion: UiEmotion,
    onEmotionSelected: (UiEmotion) -> Unit,
) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.extraLarge,
        color = Color.Transparent,
    ) {
        Row(
            modifier = Modifier
                .selectable(
                    selected = selectedEmotion == emotion,
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
                selected = selectedEmotion == emotion,
                onClick = null,
            )
            Text(text = stringResource(emotion.resourceText))
            Icon(
                modifier = Modifier.padding(horizontal = smallPadding),
                painter = painterResource(emotion.resourceIcon),
                contentDescription = null,
            )
        }
    }
}
