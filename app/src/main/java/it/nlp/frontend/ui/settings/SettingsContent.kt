package it.nlp.frontend.ui.settings

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import it.nlp.frontend.R
import it.nlp.frontend.ui.core.component.NavigateUpButton
import it.nlp.frontend.ui.core.component.VerticalSpacer
import it.nlp.frontend.ui.core.helpers.mediumPadding
import it.nlp.frontend.ui.core.helpers.smallPadding
import it.nlp.frontend.ui.core.model.UiTheme
import it.nlp.frontend.ui.settings.component.AuthenticationSettingsSection
import it.nlp.frontend.ui.settings.component.DisplaySettingsSection
import it.nlp.frontend.ui.settings.component.NotificationsSettingsSection
import it.nlp.frontend.ui.settings.component.ThemeSelectionDialog
import it.nlp.frontend.ui.settings.model.UiReminderTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsContent(
    viewState: SettingsViewState,
    onNavigateUp: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onThemeButtonClicked: () -> Unit,
    onThemeSelected: (UiTheme) -> Unit,
    onPostNotificationsPermissionDenied: () -> Unit,
    onGoToSettingsClicked: () -> Unit,
    onReminderTimeSet: () -> Unit,
    onSelectedTimeChanged: (UiReminderTime) -> Unit,
    onNotificationToggled: (Boolean) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = { NavigateUpButton(onClick = onNavigateUp) },
                title = { Text(text = stringResource(R.string.settingsTopAppBarTitle)) }
            )
        }
    ) { paddingValues ->
        val context = LocalContext.current

        LaunchedEffect(viewState) {
            if (viewState is SettingsViewState.Loaded.SavePreferredThemeFailure) {
                Toast.makeText(
                    context,
                    R.string.cannotSaveThemeErrorMessage,
                    Toast.LENGTH_SHORT,
                ).show()
            }
        }

        if (viewState is SettingsViewState.Loaded.ThemeSelectionDialog) {
            ThemeSelectionDialog(
                selectedTheme = viewState.selectedTheme,
                onThemeSelected = onThemeSelected
            )
        }

        ScaffoldBody(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(
                    start = smallPadding,
                    top = smallPadding,
                    end = smallPadding,
                    bottom = mediumPadding
                ),
            viewState = viewState,
            onThemeButtonClicked = onThemeButtonClicked,
            onNavigateToLogin = onNavigateToLogin,
            onNotificationToggled = onNotificationToggled,
            onPostNotificationsPermissionDenied = onPostNotificationsPermissionDenied,
            onReminderTimeSet = onReminderTimeSet,
            onSelectedTimeChanged = onSelectedTimeChanged,
            onGoToSettingsClicked = onGoToSettingsClicked,
        )
    }
}

@Composable
private fun ScaffoldBody(
    modifier: Modifier = Modifier,
    viewState: SettingsViewState,
    onThemeButtonClicked: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onNotificationToggled: (Boolean) -> Unit,
    onGoToSettingsClicked: () -> Unit,
    onReminderTimeSet: () -> Unit,
    onSelectedTimeChanged: (UiReminderTime) -> Unit,
    onPostNotificationsPermissionDenied: () -> Unit,
) {
    Column(
        modifier = modifier,
    ) {
        DisplaySettingsSection(
            isLoaded = viewState is SettingsViewState.Loaded,
            selectedTheme = viewState.selectedTheme,
            onThemeButtonClicked = onThemeButtonClicked,
        )

        VerticalSpacer(height = smallPadding)


        NotificationsSettingsSection(
            viewState = viewState,
            onNotificationToggled = onNotificationToggled,
            onPostNotificationsPermissionDenied = onPostNotificationsPermissionDenied,
            onReminderTimeSet = onReminderTimeSet,
            onSelectedTimeChanged = onSelectedTimeChanged,
            onGoToSettingsClicked = onGoToSettingsClicked,
        )

        VerticalSpacer(height = smallPadding)

        AuthenticationSettingsSection(
            onNavigateToLogin = onNavigateToLogin,
        )
    }
}
