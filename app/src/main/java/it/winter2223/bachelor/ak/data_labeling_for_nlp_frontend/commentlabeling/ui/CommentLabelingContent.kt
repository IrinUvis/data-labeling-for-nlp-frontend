@file:OptIn(ExperimentalMaterial3Api::class)

package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.commentlabeling.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun CommentLabelingContent() {
    Scaffold(
        topBar = { CommentLabelingTopBar() }
    ) { paddingValues ->
        Box(modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
        ) {
            LinearProgressIndicator(
                modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.Center)
            )
        }
    }
}

@Composable
fun CommentLabelingTopBar() {
    TopAppBar(
        title = { Text(text = "AppBar") }
    )
}