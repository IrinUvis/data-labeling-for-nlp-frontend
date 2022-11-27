package it.winter2223.bachelor.ak.frontend.ui.settings.component

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import it.winter2223.bachelor.ak.frontend.R
import it.winter2223.bachelor.ak.frontend.ui.core.component.HorizontalSpacer
import it.winter2223.bachelor.ak.frontend.ui.core.component.VerticalSpacer
import it.winter2223.bachelor.ak.frontend.ui.core.helpers.buttonVerticalPadding
import it.winter2223.bachelor.ak.frontend.ui.core.helpers.mediumPadding
import it.winter2223.bachelor.ak.frontend.ui.core.helpers.smallPadding
import it.winter2223.bachelor.ak.frontend.ui.core.model.UiTheme

@Composable
fun DisplaySettingsSection(
    isLoaded: Boolean,
    selectedTheme: UiTheme?,
    onThemeButtonClicked: () -> Unit,
) {
    Column {
        SectionLabel(
            modifier = Modifier.padding(start = smallPadding),
            text = stringResource(R.string.displayOptionsSettingsLabel)
        )

        VerticalSpacer(height = smallPadding)

        ThemeToggle(
            modifier = Modifier
                .fillMaxWidth(),
            isLoaded = isLoaded,
            selectedTheme = selectedTheme,
            onClick = onThemeButtonClicked,
        )
    }
}

@Composable
private fun ThemeToggle(
    modifier: Modifier = Modifier,
    isLoaded: Boolean,
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
                    enabled = isLoaded,
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

            HorizontalSpacer(width = mediumPadding)

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
