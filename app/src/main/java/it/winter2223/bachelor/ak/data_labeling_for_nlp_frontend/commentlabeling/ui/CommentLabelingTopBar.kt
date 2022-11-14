@file:Suppress("UnusedPrivateMember")

package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.commentlabeling.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentLabelingTopBar(
    modifier: Modifier = Modifier,
    onBackButtonClicked: () -> Unit,
    onSettingsButtonClicked: () -> Unit,
) {
    TopAppBar(
        modifier = modifier,
        title = { Text(text = stringResource(R.string.commentLabelingAppBarTitle)) },
        navigationIcon = {
            IconButton(onClick = onBackButtonClicked) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.goBackToPreviousScreenButtonContentDescription),
                )
            }
        },
        actions = {
            IconButton(onClick = onSettingsButtonClicked) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = stringResource(R.string.goToSettingsButtonContentDescription),
                )
            }
        },
    )
}
