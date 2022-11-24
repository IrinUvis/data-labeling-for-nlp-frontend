package it.winter2223.bachelor.ak.frontend.ui.settings.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import it.winter2223.bachelor.ak.frontend.R
import it.winter2223.bachelor.ak.frontend.ui.core.component.HorizontalSpacer
import it.winter2223.bachelor.ak.frontend.ui.core.component.VerticalSpacer
import it.winter2223.bachelor.ak.frontend.ui.core.helpers.smallPadding

@Composable
fun AuthenticationSettingsSection(
    onNavigateToLogin: () -> Unit,
) {
    Column {
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
