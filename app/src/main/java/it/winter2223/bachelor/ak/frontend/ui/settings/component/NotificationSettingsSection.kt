package it.winter2223.bachelor.ak.frontend.ui.settings.component

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import it.winter2223.bachelor.ak.frontend.R
import it.winter2223.bachelor.ak.frontend.ui.core.component.HorizontalSpacer
import it.winter2223.bachelor.ak.frontend.ui.core.component.VerticalSpacer
import it.winter2223.bachelor.ak.frontend.ui.core.helpers.mediumPadding
import it.winter2223.bachelor.ak.frontend.ui.core.helpers.smallPadding
import it.winter2223.bachelor.ak.frontend.ui.settings.SettingsViewState

@Composable
fun NotificationsSettingsSection(
    viewState: SettingsViewState,
    onNotificationToggled: (Boolean) -> Unit,
    onPostNotificationsPermissionDenied: () -> Unit,
    onGoToSettingsClicked: () -> Unit,
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                onNotificationToggled(true)
            } else {
                onPostNotificationsPermissionDenied()
            }
        }
    )

    LaunchedEffect(viewState) {
        if (viewState is SettingsViewState.Loaded.AskForNotificationPermissionDialog) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    if (viewState is SettingsViewState.Loaded.AfterPermissionDeniedDialog) {
        AlertDialog(
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

    Column {
        SectionLabel(
            modifier = Modifier.padding(start = smallPadding),
            text = stringResource(R.string.notificationsSettingsLabel)
        )

        VerticalSpacer(height = smallPadding)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = smallPadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(R.string.getDailyRemindersSettingsOption)
            )

            HorizontalSpacer(width = mediumPadding)

            val icon: (@Composable () -> Unit)? =
                if (viewState.notificationsTurnOn) {
                    {
                        Icon(
                            modifier = Modifier.size(SwitchDefaults.IconSize),
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                        )
                    }
                } else {
                    null
                }

            Switch(
                checked = viewState.notificationsTurnOn,
                thumbContent = icon,
                onCheckedChange = onNotificationToggled,
            )
        }
    }
}