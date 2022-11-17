package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.R
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.core.ui.components.HorizontalSpacer
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.core.ui.mediumPadding

@Composable
fun SignUpButton(
    onClick: () -> Unit,
    enabled: Boolean,
    isSubmittingSignUp: Boolean,
) {
    OutlinedButton(modifier = Modifier.animateContentSize(), onClick = onClick, enabled = enabled) {
        AnimatedVisibility(visible = isSubmittingSignUp) {
            Row {
                CircularProgressIndicator(modifier = Modifier.size(18.dp), strokeWidth = 2.dp)
                HorizontalSpacer(width = mediumPadding)
            }
        }
        Text(text = stringResource(R.string.signUp))
    }
}
