package it.winter2223.bachelor.ak.frontend.ui.commentlabeling.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import it.winter2223.bachelor.ak.frontend.R

@Composable
fun UnspecifiableEmotionDialog(
    onCloseDialog: () -> Unit,
    onGoToNextComment: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onCloseDialog,
        title = { Text(text = stringResource(R.string.unspecifiableEmotionDialogTitle)) },
        icon = {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = stringResource(R.string.warningIconContentDescription),
            )
        },
        text = {
            Text(text = stringResource(R.string.unspecifiableEmotionDialogSupportiveText))
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onCloseDialog()
                    onGoToNextComment()
                },
            ) {
                Text(text = stringResource(R.string.unspecifiableEmotionDialogConfirmButtonText))
            }
        },
        dismissButton = {
            TextButton(
                onClick = onCloseDialog,
            ) {
                Text(text = stringResource(R.string.unspecifiableEmotionDialogDismissButtonText))
            }
        }
    )
}
