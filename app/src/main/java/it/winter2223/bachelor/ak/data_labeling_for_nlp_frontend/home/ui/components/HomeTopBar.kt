package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.home.ui.components

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(
    onLogOutButtonClicked: () -> Unit,
) {
    CenterAlignedTopAppBar(
        title = { Text(text = "Home") },
        actions = {
            LogOutButton(onClick = onLogOutButtonClicked)
        },
    )
}

@Composable
fun LogOutButton(
    onClick: () -> Unit,
) {
    IconButton(onClick = onClick) {
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_logout_24),
            contentDescription = stringResource(id = R.string.goToSettingsButtonContentDescription),
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
