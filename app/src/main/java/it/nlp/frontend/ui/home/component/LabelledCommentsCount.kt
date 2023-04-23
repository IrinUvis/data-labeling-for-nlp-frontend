package it.nlp.frontend.ui.home.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Badge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import it.nlp.frontend.R
import it.nlp.frontend.ui.core.helpers.extraSmallPadding
import it.nlp.frontend.ui.core.helpers.mediumPadding

@Composable
@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
fun LabelledTextsCount(assignmentsCount: Int) {
    Row(
        modifier = Modifier
            .padding(bottom = mediumPadding)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Text(
            text = stringResource(R.string.yourAssignments),
            style = MaterialTheme.typography.titleLarge,
        )
        Badge(
            containerColor = MaterialTheme.colorScheme.surfaceTint
        ) {
            AnimatedContent(targetState = assignmentsCount) { count ->
                Text(
                    modifier = Modifier.padding(horizontal = extraSmallPadding),
                    text = count.toString(),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}
