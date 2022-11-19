package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.home.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.R

@Composable
fun LabelCommentsInstructions() {
    Text(
        text = stringResource(R.string.labelCommentsHomeInstructions),
        style = MaterialTheme.typography.bodyLarge,
        textAlign = TextAlign.Center,
    )
}