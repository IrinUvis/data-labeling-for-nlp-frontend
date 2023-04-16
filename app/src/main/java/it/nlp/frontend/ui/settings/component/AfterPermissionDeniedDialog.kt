package it.nlp.frontend.ui.settings.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import it.nlp.frontend.R

@Composable
fun AfterPermissionDeniedDialog(
    modifier: Modifier = Modifier,
    onNotificationToggled: (Boolean) -> Unit,
    onGoToSettingsClicked: () -> Unit,
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = { onNotificationToggled(false) },
        title = { Text(text = stringResource(R.string.afterPermissionDeniedDialogTitle)) },
        text = { Text(text = stringResource(R.string.afterPermissionDeniedDialogText)) },
        dismissButton = {
            TextButton(
                onClick = { onNotificationToggled(false) },
            ) {
                Text(text = stringResource(R.string.afterPermissionDeniedDialogDismissButtonText))
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onGoToSettingsClicked()
                    onNotificationToggled(false)
                },
            ) {
                Text(text = stringResource(R.string.afterPermissionDeniedDialogConfirmButtonText))
            }
        },
    )
}
