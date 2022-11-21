package it.winter2223.bachelor.ak.frontend.ui.login.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import it.winter2223.bachelor.ak.frontend.ui.core.helpers.getString
import it.winter2223.bachelor.ak.frontend.ui.login.LogInViewState

@Composable
fun AnimatedSubmissionError(viewState: LogInViewState) {
    AnimatedVisibility(visible = viewState is LogInViewState.SubmissionError) {
        val context = LocalContext.current
        val errorMessage by remember {
            mutableStateOf(
                (viewState as? LogInViewState.SubmissionError)?.errorMessage?.getString(context)
            )
        }
        errorMessage?.let {
            Text(
                modifier = Modifier
                    .padding(top = 12.dp),
                text = it,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.error,
            )
        }
    }
}
