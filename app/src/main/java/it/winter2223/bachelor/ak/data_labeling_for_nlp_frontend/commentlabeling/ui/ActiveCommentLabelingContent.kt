@file:OptIn(ExperimentalMaterial3Api::class)

package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.commentlabeling.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun ActiveCommentLabelingContent(
    onSettingsButtonPressed: () -> Unit,
) {
    Scaffold(
        topBar = {
            CommentLabelingTopBar(
                onSettingsButtonPressed = onSettingsButtonPressed,
            )
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
        ) {
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.Center),
            ) {
                Text(text = "Comments loaded")
            }
        }
    }
}

@Composable
fun CommentLabelingTopBar(
    onSettingsButtonPressed: () -> Unit,
) {
    TopAppBar(
        title = { Text(text = "AppBar") },
        actions = {
            IconButton(onClick = onSettingsButtonPressed) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Go to Settings button",
                )
            }
        },
    )
}
