package it.winter2223.bachelor.ak.frontend.ui.settings

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.window.DialogProperties
import it.winter2223.bachelor.ak.frontend.R
import it.winter2223.bachelor.ak.frontend.ui.core.component.HorizontalSpacer
import it.winter2223.bachelor.ak.frontend.ui.core.component.NavigateUpButton
import it.winter2223.bachelor.ak.frontend.ui.core.component.VerticalSpacer
import it.winter2223.bachelor.ak.frontend.ui.core.helpers.bigPadding
import it.winter2223.bachelor.ak.frontend.ui.core.helpers.buttonHorizontalPadding
import it.winter2223.bachelor.ak.frontend.ui.core.helpers.buttonVerticalPadding
import it.winter2223.bachelor.ak.frontend.ui.core.helpers.mediumPadding
import it.winter2223.bachelor.ak.frontend.ui.core.helpers.smallPadding
import it.winter2223.bachelor.ak.frontend.ui.settings.model.UiTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsContent(
    viewState: SettingsViewState,
    onNavigateUp: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onThemeButtonClicked: () -> Unit,
    onThemeSelected: (UiTheme) -> Unit,
    onNotificationToggled: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = { NavigateUpButton(onClick = onNavigateUp) },
                title = { Text(text = stringResource(R.string.settingsTopAppBarTitle)) }
            )
        }
    ) { paddingValues ->
        if (viewState is SettingsViewState.Loaded.ThemeSelectionDialog) {
            AlertDialog(
                onDismissRequest = { onThemeSelected(viewState.selectedTheme) },
                title = { Text(text = stringResource(R.string.themeSelectionDialogTitle)) },
                text = {
                    Column {
                        for (theme in UiTheme.values()) {
                            Surface(
                                shape = MaterialTheme.shapes.extraSmall,
                                color = Color.Transparent,
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable(
                                            onClick = { onThemeSelected(theme) }
                                        )
                                        .padding(
                                            end = buttonHorizontalPadding,
                                            top = buttonVerticalPadding,
                                            bottom = buttonVerticalPadding,
                                        ),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    RadioButton(
                                        modifier = Modifier.padding(end = mediumPadding),
                                        selected = theme == viewState.selectedTheme,
                                        onClick = null,
                                    )
                                    Text(text = stringResource(theme.resId), style = MaterialTheme.typography.bodyLarge)
                                }
                            }
                        }
                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            onThemeSelected(viewState.pickedTheme)
                        },
                    ) {
                        Text(text = stringResource(R.string.themeSelectionDialogConfirmButtonText))
                    }
                },
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(
                    start = smallPadding,
                    top = smallPadding,
                    end = smallPadding,
                    bottom = mediumPadding
                ),
        ) {
            SectionLabel(
                modifier = Modifier.padding(start = smallPadding),
                text = stringResource(R.string.displayOptionsSettingsLabel)
            )

            VerticalSpacer(height = smallPadding)

            ThemeToggle(
                modifier = Modifier
                    .fillMaxWidth(),
                viewState = viewState,
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
}

@Composable
private fun LogOutButton(
    modifier: Modifier = Modifier,
    onNavigateToLogin: () -> Unit,
) {
    FilledTonalButton(
        modifier = modifier,
        onClick = onNavigateToLogin
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_baseline_logout_24),
            contentDescription = stringResource(R.string.logOutIconContentDescription),
        )
        HorizontalSpacer(width = smallPadding)
        Text(text = stringResource(R.string.logOutSettingsButtonText))
    }
}

@Composable
fun SectionLabel(
    modifier: Modifier = Modifier,
    text: String,
) {
    Text(
        modifier = modifier,
        style = MaterialTheme.typography.labelLarge.copy(
            color = MaterialTheme.colorScheme.primary,
        ),
        text = text,
    )
}

@Composable
fun ThemeToggle(
    modifier: Modifier = Modifier,
    viewState: SettingsViewState,
    onClick: () -> Unit,
    selectedTheme: UiTheme?,
) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        color = Color.Transparent,
    ) {
        Row(
            modifier = Modifier
                .clickable(
                    enabled = viewState is SettingsViewState.Loaded,
                    onClick = onClick
                )
                .padding(
                    start = smallPadding,
                    top = buttonVerticalPadding,
                    end = smallPadding,
                    bottom = buttonVerticalPadding,
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(text = stringResource(R.string.themeToggleText), maxLines = 1, overflow = TextOverflow.Ellipsis)
            Crossfade(targetState = selectedTheme != null) { themeSelected ->
                if (themeSelected) {
                    Text(
                        text = stringResource(selectedTheme!!.resId),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        }
    }
}
