package it.nlp.frontend.ui.commentlabeling.component

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import it.nlp.frontend.ui.core.component.verticalScrollbar
import it.nlp.frontend.ui.core.helpers.mediumPadding
import it.nlp.frontend.ui.core.helpers.smallPadding

@Composable
fun CommentCard(
    modifier: Modifier = Modifier,
    scrollState: ScrollState,
    text: String,
) {
    Card(modifier = modifier) {
        Crossfade(targetState = text) { commentText ->
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(
                    vertical = mediumPadding,
                    horizontal = smallPadding,
                )
                .verticalScrollbar(scrollState)
                .padding(horizontal = smallPadding)
                .verticalScroll(scrollState)
            ) {
                CommentText(
                    modifier = Modifier
                        .align(Alignment.Center),
                    text = commentText,
                )
            }
        }
    }
}

@Composable
fun CommentText(
    modifier: Modifier = Modifier,
    text: String,
) {
    Text(
        modifier = modifier,
        text = "\"$text\"",
        style = MaterialTheme.typography.bodyMedium,
        textAlign = TextAlign.Center,
    )
}
