package it.winter2223.bachelor.ak.frontend.ui.home.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import it.winter2223.bachelor.ak.frontend.R

@Composable
fun LabelCommentsInstructions(
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier,
        text = stringResource(R.string.labelCommentsHomeInstructions),
        style = MaterialTheme.typography.bodyLarge,
        textAlign = TextAlign.Center,
    )
}
