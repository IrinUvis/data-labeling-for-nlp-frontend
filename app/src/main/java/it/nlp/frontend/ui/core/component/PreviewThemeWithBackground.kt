package it.nlp.frontend.ui.core.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import it.nlp.frontend.ui.core.theme.AppTheme

@Composable
fun PreviewThemeWithBackground(darkTheme: Boolean, content: @Composable () -> Unit) {
    AppTheme(
        darkTheme = darkTheme,
    ) {
        Surface(color = MaterialTheme.colorScheme.background, content = content)
    }
}
