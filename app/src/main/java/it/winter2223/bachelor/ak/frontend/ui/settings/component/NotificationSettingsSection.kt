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
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import it.winter2223.bachelor.ak.frontend.R
import it.winter2223.bachelor.ak.frontend.ui.core.component.HorizontalSpacer
import it.winter2223.bachelor.ak.frontend.ui.core.component.NumberPicker
import it.winter2223.bachelor.ak.frontend.ui.core.component.VerticalSpacer
import it.winter2223.bachelor.ak.frontend.ui.core.helpers.bigPadding
import it.winter2223.bachelor.ak.frontend.ui.core.helpers.extraSmallPadding
import it.winter2223.bachelor.ak.frontend.ui.core.helpers.mediumPadding
import it.winter2223.bachelor.ak.frontend.ui.core.helpers.smallPadding
import it.winter2223.bachelor.ak.frontend.ui.settings.SettingsViewState
import it.winter2223.bachelor.ak.frontend.ui.settings.model.RemindersState
import it.winter2223.bachelor.ak.frontend.ui.settings.model.UiReminderTime

private const val MIN_HOUR = 0
private const val MAX_HOUR = 23
private const val MIN_MINUTE = 0
private const val MAX_MINUTE = 59
private const val DIGIT_LOWER_BOUNDARY = 0
private const val DIGIT_UPPER_BOUNDARY = 9

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
private fun TimePicker(
    remindersState: RemindersState,
    onReminderTimeSet: () -> Unit,
    onSelectedTimeChanged: (UiReminderTime) -> Unit,
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = smallPadding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        NumberPicker(
            value = remindersState.selectedReminderTime.hourOfDay,
            label = { it.toDoubleCharString() },
            onValueChange = { hour ->
                onSelectedTimeChanged(
                    UiReminderTime(
                        hour,
                        remindersState.selectedReminderTime.minute,
                    ),
                )
            },
            range = MIN_HOUR..MAX_HOUR,
        )
        HorizontalSpacer(width = extraSmallPadding)
        Text(text = ":", style = MaterialTheme.typography.titleLarge)
        HorizontalSpacer(width = extraSmallPadding)
        NumberPicker(
            value = remindersState.selectedReminderTime.minute,
            label = { it.toDoubleCharString() },
            onValueChange = { minute ->
                onSelectedTimeChanged(
                    UiReminderTime(
                        remindersState.selectedReminderTime.hourOfDay,
                        minute
                    )
                )
            },
            range = MIN_MINUTE..MAX_MINUTE,
        )
        HorizontalSpacer(width = bigPadding)
        Button(
            onClick = onReminderTimeSet,
            enabled = !(remindersState.selectedReminderTime.hourOfDay == remindersState.scheduledReminderTime.hourOfDay
                    && remindersState.selectedReminderTime.minute == remindersState.scheduledReminderTime.minute),
        )
        {
            Text(
                text = stringResource(R.string.timeSelectorButtonText),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

private fun Int.toDoubleCharString(): String {
    return if (this in DIGIT_LOWER_BOUNDARY..DIGIT_UPPER_BOUNDARY) {
        "0$this"
    } else {
        toString()
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
