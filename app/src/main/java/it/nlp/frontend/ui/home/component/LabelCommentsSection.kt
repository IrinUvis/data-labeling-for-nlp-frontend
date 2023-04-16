package it.nlp.frontend.ui.home.component

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import it.nlp.frontend.R
import it.nlp.frontend.ui.core.component.HorizontalSpacer
import it.nlp.frontend.ui.core.component.NumberPicker
import it.nlp.frontend.ui.core.helpers.mediumPadding

@Composable
fun LabelCommentsSection(
    modifier: Modifier = Modifier,
    numberOfCommentsToLabel: Int,
    onGoToCommentLabelingClicked: () -> Unit,
    onNumberOfCommentsToLabelUpdated: (Int) -> Unit,
    selectionRange: IntRange,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Button(onClick = onGoToCommentLabelingClicked) {
            Text(text = stringResource(R.string.labelComments))
        }
        HorizontalSpacer(width = mediumPadding)
        NumberPicker(
            modifier = Modifier
                .animateContentSize(),
            value = numberOfCommentsToLabel,
            onValueChange = onNumberOfCommentsToLabelUpdated,
            range = selectionRange,
        )
    }
}
