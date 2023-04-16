package it.nlp.frontend.ui.core.component

import androidx.compose.foundation.ScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.verticalScrollbar(
    state: ScrollState,
    width: Dp = 4.dp,
): Modifier = composed {
    val color = MaterialTheme.colorScheme.primary

    drawWithContent {
        drawContent()

        val isScrollable = state.maxValue > 0 && state.maxValue != Int.MAX_VALUE

        if (isScrollable) {
            val height = this.size.height
            val value = state.value.toFloat()
            val maxValue = state.maxValue.toFloat()

            val visiblePercentage = height / (maxValue + height)
            val scrollbarOffsetY = value * visiblePercentage
            val scrollbarHeight = height * visiblePercentage

            drawRoundRect(
                color = color,
                topLeft = Offset(this.size.width - width.toPx(), scrollbarOffsetY),
                size = Size(width.toPx(), scrollbarHeight),
                cornerRadius = CornerRadius((width / 2).toPx(), (width / 2).toPx()),
            )
        }
    }
}
