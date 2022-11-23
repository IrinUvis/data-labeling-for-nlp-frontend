package it.winter2223.bachelor.ak.frontend.ui.settings

import it.winter2223.bachelor.ak.frontend.ui.core.model.UiTheme

sealed class SettingsViewState(
    open val selectedTheme: UiTheme?,
    open val notificationsTurnOn: Boolean,
) {
    object Loading : SettingsViewState(
        selectedTheme = null,
        notificationsTurnOn = false,
    )

    sealed class Loaded(
        override val selectedTheme: UiTheme,
        override val notificationsTurnOn: Boolean,
    ) : SettingsViewState(
        selectedTheme = selectedTheme,
        notificationsTurnOn = notificationsTurnOn,
    ) {
        data class Active(
            override val selectedTheme: UiTheme,
            override val notificationsTurnOn: Boolean,
        ) : Loaded(
            selectedTheme = selectedTheme,
            notificationsTurnOn = notificationsTurnOn,
        )

        data class ThemeSelectionDialog(
            override val selectedTheme: UiTheme,
            override val notificationsTurnOn: Boolean,
        ) : Loaded(
            selectedTheme = selectedTheme,
            notificationsTurnOn = notificationsTurnOn,
        )

        data class AskForNotificationPermissionDialog(
            override val selectedTheme: UiTheme,
        ) : Loaded(
            selectedTheme = selectedTheme,
            notificationsTurnOn = false,
        )

        data class SavePreferredThemeFailure(
            override val selectedTheme: UiTheme,
            override val notificationsTurnOn: Boolean,
        ) : Loaded(
            selectedTheme = selectedTheme,
            notificationsTurnOn = notificationsTurnOn,
        )
    }
}
