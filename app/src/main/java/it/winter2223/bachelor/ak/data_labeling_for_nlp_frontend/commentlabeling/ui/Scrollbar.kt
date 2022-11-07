package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.commentlabeling.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Suppress("MagicNumber")
fun Modifier.simpleVerticalScrollbar(
    state: ScrollState,
    width: Dp = 4.dp,
): Modifier = composed {
    val targetAlpha = if (state.isScrollInProgress) 1f else 0f
    val duration = if (state.isScrollInProgress) 150 else 500
    val color = MaterialTheme.colorScheme.primary

    val alpha by animateFloatAsState(
        targetValue = targetAlpha,
        animationSpec = tween(durationMillis = duration)
    )

    drawWithContent {
        drawContent()

        val needDrawScrollbar = state.isScrollInProgress || alpha > 0.0f

        // Draw scrollbar if scrolling or if the animation is still running and lazy column has content
        if (needDrawScrollbar) {
            val height = this.size.height
            val value = state.value.toFloat()
            val maxValue = state.maxValue.toFloat()

            val visiblePercentage = height / (maxValue + height) // percent of how much is visible of all the text
            val scrollbarOffsetY = value * visiblePercentage
            val scrollbarHeight = height * visiblePercentage

            drawRoundRect(
                color = color,
                topLeft = Offset(this.size.width - width.toPx(), scrollbarOffsetY),
                size = Size(width.toPx(), scrollbarHeight),
                cornerRadius = CornerRadius((width / 2).toPx(), (width / 2).toPx()),
                alpha = alpha
            )
        }
    }
}
