package it.winter2223.bachelor.ak.frontend.ui.settings.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import it.winter2223.bachelor.ak.frontend.R
import it.winter2223.bachelor.ak.frontend.ui.core.component.HorizontalSpacer
import it.winter2223.bachelor.ak.frontend.ui.core.component.NumberPicker
import it.winter2223.bachelor.ak.frontend.ui.core.helpers.bigPadding
import it.winter2223.bachelor.ak.frontend.ui.core.helpers.extraSmallPadding
import it.winter2223.bachelor.ak.frontend.ui.core.helpers.smallPadding
import it.winter2223.bachelor.ak.frontend.ui.settings.model.RemindersState
import it.winter2223.bachelor.ak.frontend.ui.settings.model.UiReminderTime

private const val MIN_HOUR = 0
private const val MAX_HOUR = 23
private const val MIN_MINUTE = 0
private const val MAX_MINUTE = 59
private const val DIGIT_LOWER_BOUNDARY = 0
private const val DIGIT_UPPER_BOUNDARY = 9

@Composable
fun TimePicker(
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
