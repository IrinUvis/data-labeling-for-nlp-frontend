package it.winter2223.bachelor.ak.frontend.ui.settings.component

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
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
import it.winter2223.bachelor.ak.frontend.ui.settings.model.UiReminderTime

@Composable
fun NotificationsSettingsSection(
    viewState: SettingsViewState,
    onNotificationToggled: (Boolean) -> Unit,
    onPostNotificationsPermissionDenied: () -> Unit,
    onReminderTimeSet: () -> Unit,
    onSelectedTimeChanged: (UiReminderTime) -> Unit,
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
        AfterPermissionDeniedDialog(
            onNotificationToggled = onNotificationToggled,
            onGoToSettingsClicked = onGoToSettingsClicked,
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

            Switch(
                checked = viewState.remindersState.turnOn,
                thumbContent = { IconForSwitch(notificationsTurnOn = viewState.remindersState.turnOn) },
                onCheckedChange = onNotificationToggled,
            )
        }

        AnimatedVisibility(visible = viewState.remindersState.turnOn) {
            VerticalSpacer(height = smallPadding)

            TimePicker(
                remindersState = viewState.remindersState,
                onReminderTimeSet = onReminderTimeSet,
                onSelectedTimeChanged = onSelectedTimeChanged,
            )
        }
    }
}

@Composable
private fun IconForSwitch(
    notificationsTurnOn: Boolean,
) {
    if (notificationsTurnOn) {
        Icon(
            modifier = Modifier.size(SwitchDefaults.IconSize),
            imageVector = Icons.Default.Check,
            contentDescription = null,
        )
    }
}
