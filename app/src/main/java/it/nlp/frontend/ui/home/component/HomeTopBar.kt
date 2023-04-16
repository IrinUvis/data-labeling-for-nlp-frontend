package it.nlp.frontend.ui.home.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import it.nlp.frontend.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(
    onSettingsButtonClicked: () -> Unit,
) {
    CenterAlignedTopAppBar(
        title = { Text(text = stringResource(R.string.homeTopBarTitle)) },
        actions = {
            GoToSettingsButton(onClick = onSettingsButtonClicked)
        },
    )
}

@Composable
fun GoToSettingsButton(
    onClick: () -> Unit,
) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Default.Settings,
            contentDescription = stringResource(R.string.goToSettingsButtonContentDescription),
        )
    }
}

//@Composable
//fun SettingsButton(
//    onClick: () -> Unit,
//) {
//    IconButton(onClick = onClick) {
//        Icon(
//            imageVector = Icons.Default.Settings,
//            contentDescription = stringResource(id = R.string.goToSettingsButtonContentDescription),
//        )
//    }
//}
