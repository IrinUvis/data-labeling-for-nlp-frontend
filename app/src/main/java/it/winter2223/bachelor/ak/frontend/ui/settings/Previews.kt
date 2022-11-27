@file:Suppress("UnusedPrivateMember", "TooManyFunctions")

package it.winter2223.bachelor.ak.frontend.ui.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import it.winter2223.bachelor.ak.frontend.R
import it.winter2223.bachelor.ak.frontend.ui.core.component.PreviewThemeWithBackground
import it.winter2223.bachelor.ak.frontend.ui.core.model.UiTheme
import it.winter2223.bachelor.ak.frontend.ui.settings.component.AfterPermissionDeniedDialog
import it.winter2223.bachelor.ak.frontend.ui.settings.component.AuthenticationSettingsSection
import it.winter2223.bachelor.ak.frontend.ui.settings.component.DisplaySettingsSection
import it.winter2223.bachelor.ak.frontend.ui.settings.component.NotificationsSettingsSection
import it.winter2223.bachelor.ak.frontend.ui.settings.component.SectionLabel
import it.winter2223.bachelor.ak.frontend.ui.settings.component.ThemeSelectionDialog
import it.winter2223.bachelor.ak.frontend.ui.settings.component.TimePicker
import it.winter2223.bachelor.ak.frontend.ui.settings.model.RemindersState
import it.winter2223.bachelor.ak.frontend.ui.settings.model.UiReminderTime

@Preview
@Composable
fun DarkSettingsContentPreview(
    @PreviewParameter(SettingsContentPreviewParameterProvider::class) viewState: SettingsViewState,
) {
    PreviewThemeWithBackground(darkTheme = true) {
        SettingsContent(
            viewState = viewState,
            onNavigateUp = { },
            onNavigateToLogin = { },
            onThemeButtonClicked = { },
            onThemeSelected = { },
            onPostNotificationsPermissionDenied = { },
            onGoToSettingsClicked = { },
            onReminderTimeSet = { },
            onSelectedTimeChanged = { },
            onNotificationToggled = { },
        )
    }
}

@Preview
@Composable
fun LightSettingsContentPreview(
    @PreviewParameter(SettingsContentPreviewParameterProvider::class) viewState: SettingsViewState,
) {
    PreviewThemeWithBackground(darkTheme = false) {
        SettingsContent(
            viewState = viewState,
            onNavigateUp = { },
            onNavigateToLogin = { },
            onThemeButtonClicked = { },
            onThemeSelected = { },
            onPostNotificationsPermissionDenied = { },
            onGoToSettingsClicked = { },
            onReminderTimeSet = { },
            onSelectedTimeChanged = { },
            onNotificationToggled = { },
        )
    }
}

@Preview
@Composable
fun DarkAfterPermissionDeniedDialogPreview() {
    PreviewThemeWithBackground(darkTheme = true) {
        AfterPermissionDeniedDialog(
            onNotificationToggled = { },
            onGoToSettingsClicked = { },
        )
    }
}

@Preview
@Composable
fun LightAfterPermissionDeniedDialogPreview() {
    PreviewThemeWithBackground(darkTheme = false) {
        AfterPermissionDeniedDialog(
            onNotificationToggled = { },
            onGoToSettingsClicked = { },
        )
    }
}

@Preview
@Composable
fun DarkAuthenticationSettingsSectionPreview() {
    PreviewThemeWithBackground(darkTheme = true) {
        AuthenticationSettingsSection(
            onNavigateToLogin = { }
        )
    }
}

@Preview
@Composable
fun LightAuthenticationSettingsSectionPreview() {
    PreviewThemeWithBackground(darkTheme = false) {
        AuthenticationSettingsSection(
            onNavigateToLogin = { }
        )
    }
}

@Preview
@Composable
fun DarkDisplaySettingsSectionPreview() {
    PreviewThemeWithBackground(darkTheme = true) {
        DisplaySettingsSection(
            isLoaded = true,
            selectedTheme = UiTheme.Dark,
            onThemeButtonClicked = { },
        )
    }
}

@Preview
@Composable
fun LightDisplaySettingsSectionPreview() {
    PreviewThemeWithBackground(darkTheme = false) {
        DisplaySettingsSection(
            isLoaded = false,
            selectedTheme = UiTheme.Light,
            onThemeButtonClicked = { },
        )
    }
}

@Preview
@Composable
fun DarkNotificationSettingsSectionPreview() {
    PreviewThemeWithBackground(darkTheme = true) {
        NotificationsSettingsSection(
            viewState = SettingsViewState.Loaded.Active(
                selectedTheme = UiTheme.Dark,
                remindersState = RemindersState(
                    turnOn = true,
                    scheduledReminderTime = UiReminderTime(),
                    selectedReminderTime = UiReminderTime(hourOfDay = 1)
                )
            ),
            onNotificationToggled = { },
            onPostNotificationsPermissionDenied = { },
            onReminderTimeSet = { },
            onSelectedTimeChanged = { },
            onGoToSettingsClicked = { },
        )
    }
}

@Preview
@Composable
fun DarkNotificationSettingsSectionWithSameScheduledAndSelectedTimePreview() {
    PreviewThemeWithBackground(darkTheme = true) {
        NotificationsSettingsSection(
            viewState = SettingsViewState.Loaded.Active(
                selectedTheme = UiTheme.Dark,
                remindersState = RemindersState(
                    turnOn = true,
                    scheduledReminderTime = UiReminderTime(),
                    selectedReminderTime = UiReminderTime()
                )
            ),
            onNotificationToggled = { },
            onPostNotificationsPermissionDenied = { },
            onReminderTimeSet = { },
            onSelectedTimeChanged = { },
            onGoToSettingsClicked = { },
        )
    }
}

@Preview
@Composable
fun LightNotificationSettingsSectionPreview() {
    PreviewThemeWithBackground(darkTheme = false) {
        NotificationsSettingsSection(
            viewState = SettingsViewState.Loaded.Active(
                selectedTheme = UiTheme.Light,
                remindersState = RemindersState(
                    turnOn = false,
                    scheduledReminderTime = UiReminderTime(),
                    selectedReminderTime = UiReminderTime(hourOfDay = 1)
                )
            ),
            onNotificationToggled = { },
            onPostNotificationsPermissionDenied = { },
            onReminderTimeSet = { },
            onSelectedTimeChanged = { },
            onGoToSettingsClicked = { },
        )
    }
}

@Preview
@Composable
fun DarkSectionLabelPreview() {
    PreviewThemeWithBackground(darkTheme = true) {
        SectionLabel(text = stringResource(id = R.string.shortPreviewText))
    }
}

@Preview
@Composable
fun LightSectionLabelPreview() {
    PreviewThemeWithBackground(darkTheme = false) {
        SectionLabel(text = stringResource(id = R.string.shortPreviewText))
    }
}

@Preview
@Composable
fun DarkThemeSelectionDialogPreview() {
    PreviewThemeWithBackground(darkTheme = true) {
        ThemeSelectionDialog(
            selectedTheme = UiTheme.Dark,
            onThemeSelected = { },
        )
    }
}

@Preview
@Composable
fun LightThemeSelectionDialogPreview() {
    PreviewThemeWithBackground(darkTheme = false) {
        ThemeSelectionDialog(
            selectedTheme = UiTheme.Light,
            onThemeSelected = { },
        )
    }
}

@Preview
@Composable
fun DarkTimePickerPreview() {
    PreviewThemeWithBackground(darkTheme = true) {
        TimePicker(
            remindersState = RemindersState(
                turnOn = false,
                scheduledReminderTime = UiReminderTime(),
                selectedReminderTime = UiReminderTime(hourOfDay = 1)
            ),
            onReminderTimeSet = { },
            onSelectedTimeChanged = {}
        )
    }
}

@Preview
@Composable
fun LightTimePickerPreview() {
    PreviewThemeWithBackground(darkTheme = false) {
        TimePicker(
            remindersState = RemindersState(
                turnOn = false,
                scheduledReminderTime = UiReminderTime(),
                selectedReminderTime = UiReminderTime()
            ),
            onReminderTimeSet = { },
            onSelectedTimeChanged = {}
        )
    }
}

class SettingsContentPreviewParameterProvider : PreviewParameterProvider<SettingsViewState> {
    override val values: Sequence<SettingsViewState>
        get() {
            val defaultSelectedTime = UiReminderTime()
            val defaultScheduledTime = UiReminderTime()

            return sequenceOf(
                SettingsViewState.Loading,
                SettingsViewState.Loaded.Active(
                    selectedTheme = UiTheme.System,
                    remindersState = RemindersState(
                        turnOn = false,
                        scheduledReminderTime = defaultScheduledTime,
                        selectedReminderTime = defaultSelectedTime,
                    )
                ),
                SettingsViewState.Loaded.Active(
                    selectedTheme = UiTheme.Dark,
                    remindersState = RemindersState(
                        turnOn = false,
                        scheduledReminderTime = defaultScheduledTime,
                        selectedReminderTime = defaultSelectedTime,
                    )
                ),
                SettingsViewState.Loaded.Active(
                    selectedTheme = UiTheme.Light,
                    remindersState = RemindersState(
                        turnOn = true,
                        scheduledReminderTime = defaultScheduledTime,
                        selectedReminderTime = defaultSelectedTime,
                    )
                ),
                SettingsViewState.Loaded.ThemeSelectionDialog(
                    selectedTheme = UiTheme.Dark,
                    remindersState = RemindersState(
                        turnOn = true,
                        scheduledReminderTime = defaultScheduledTime,
                        selectedReminderTime = defaultSelectedTime,
                    )
                ),
                SettingsViewState.Loaded.SavePreferredThemeFailure(
                    selectedTheme = UiTheme.Light,
                    remindersState = RemindersState(
                        turnOn = true,
                        scheduledReminderTime = defaultScheduledTime,
                        selectedReminderTime = defaultSelectedTime,
                    )
                ),
                SettingsViewState.Loaded.AskForNotificationPermissionDialog(
                    selectedTheme = UiTheme.System,
                    reminderTimes = defaultScheduledTime,
                ),
                SettingsViewState.Loaded.AfterPermissionDeniedDialog(
                    selectedTheme = UiTheme.System,
                    reminderTimes = defaultScheduledTime,
                ),
            )
        }
}
