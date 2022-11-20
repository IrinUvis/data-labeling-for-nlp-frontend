package it.winter2223.bachelor.ak.frontend.ui.core.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import it.winter2223.bachelor.ak.frontend.R

@Composable
fun NavigateUpButton(
    onClick: () -> Unit,
) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = stringResource(id = R.string.goBackToPreviousScreenButtonContentDescription),
        )
    }
}
