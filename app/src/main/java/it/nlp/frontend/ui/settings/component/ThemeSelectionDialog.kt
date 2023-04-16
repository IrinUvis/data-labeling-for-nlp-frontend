package it.nlp.frontend.ui.settings.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import it.nlp.frontend.R
import it.nlp.frontend.ui.core.helpers.buttonVerticalPadding
import it.nlp.frontend.ui.core.helpers.mediumPadding
import it.nlp.frontend.ui.core.model.UiTheme

@Composable
fun ThemeSelectionDialog(
    modifier: Modifier = Modifier,
    selectedTheme: UiTheme,
    onThemeSelected: (UiTheme) -> Unit,
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = { onThemeSelected(selectedTheme) },
        title = { Text(text = stringResource(R.string.themeSelectionDialogTitle)) },
        text = {
            Column {
                for (theme in UiTheme.values()) {
                    Surface(
                        shape = MaterialTheme.shapes.extraSmall,
                        color = Color.Transparent,
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(
                                    onClick = { onThemeSelected(theme) }
                                )
                                .padding(
                                    vertical = buttonVerticalPadding,
                                ),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            RadioButton(
                                modifier = Modifier.padding(end = mediumPadding),
                                selected = theme == selectedTheme,
                                onClick = null,
                            )
                            Text(
                                text = stringResource(theme.resId),
                                style = MaterialTheme.typography.bodyLarge,
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onThemeSelected(selectedTheme)
                },
            ) {
                Text(text = stringResource(R.string.themeSelectionDialogConfirmButtonText))
            }
        },
    )
}
