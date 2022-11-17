package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.core.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.core.ui.theme.AppTheme

@Composable
fun PreviewThemeWithBackground(darkTheme: Boolean, content: @Composable () -> Unit) {
    AppTheme(
        darkTheme = darkTheme,
    ) {
        Surface(color = MaterialTheme.colorScheme.background, content = content)
    }
}
