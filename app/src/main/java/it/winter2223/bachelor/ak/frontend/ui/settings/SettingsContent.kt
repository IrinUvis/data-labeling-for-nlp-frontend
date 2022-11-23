package it.winter2223.bachelor.ak.frontend.ui.settings

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import it.winter2223.bachelor.ak.frontend.R
import it.winter2223.bachelor.ak.frontend.ui.core.component.NavigateUpButton
import it.winter2223.bachelor.ak.frontend.ui.core.component.VerticalSpacer
import it.winter2223.bachelor.ak.frontend.ui.core.helpers.mediumPadding
import it.winter2223.bachelor.ak.frontend.ui.core.helpers.smallPadding
import it.winter2223.bachelor.ak.frontend.ui.settings.component.LogOutButton
import it.winter2223.bachelor.ak.frontend.ui.settings.component.SectionLabel
import it.winter2223.bachelor.ak.frontend.ui.settings.component.ThemeSelectionDialog
import it.winter2223.bachelor.ak.frontend.ui.settings.component.ThemeToggle
import it.winter2223.bachelor.ak.frontend.ui.core.model.UiTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsContent(
    viewState: SettingsViewState,
    onNavigateUp: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onThemeButtonClicked: () -> Unit,
    onThemeSelected: (UiTheme) -> Unit,
//    onNotificationToggled: () -> Unit,
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
        )
    }
}

@Composable
private fun ScaffoldBody(
    modifier: Modifier = Modifier,
    viewState: SettingsViewState,
    onThemeButtonClicked: () -> Unit,
    onNavigateToLogin: () -> Unit,
) {
    Column(
        modifier = modifier,
    ) {
        SectionLabel(
            modifier = Modifier.padding(start = smallPadding),
            text = stringResource(R.string.displayOptionsSettingsLabel)
        )

        VerticalSpacer(height = smallPadding)

        ThemeToggle(
            modifier = Modifier
                .fillMaxWidth(),
            isLoaded = viewState is SettingsViewState.Loaded,
            selectedTheme = viewState.selectedTheme,
            onClick = onThemeButtonClicked,
        )

        VerticalSpacer(height = smallPadding)

        SectionLabel(
            modifier = Modifier.padding(start = smallPadding),
            text = stringResource(R.string.AuthenticationOptionsSettingsLabel)
        )

        VerticalSpacer(height = smallPadding)

        LogOutButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = smallPadding),
            onNavigateToLogin = onNavigateToLogin
        )
    }
}








