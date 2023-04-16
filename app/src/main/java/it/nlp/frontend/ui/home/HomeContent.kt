package it.nlp.frontend.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import it.nlp.frontend.ui.core.helpers.mediumPadding
import it.nlp.frontend.ui.core.helpers.smallPadding
import it.nlp.frontend.ui.home.component.HomeTopBar
import it.nlp.frontend.ui.home.component.LabelCommentsInstructions
import it.nlp.frontend.ui.home.component.LabelCommentsSection

private const val MIN_COMMENT_QUANTITY = 5
private const val MAX_COMMENT_QUANTITY = 20

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
    viewState: HomeViewState.Loaded,
    onSettingsButtonClicked: () -> Unit,
    onGoToCommentLabelingClicked: () -> Unit,
    onNumberOfCommentsToLabelUpdated: (Int) -> Unit,
) {
    Scaffold(
        topBar = {
            HomeTopBar(
                onSettingsButtonClicked = onSettingsButtonClicked,
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .padding(
                    start = smallPadding,
                    top = smallPadding,
                    end = smallPadding,
                    bottom = mediumPadding
                )
                .fillMaxSize(),
        ) {
            Column {
                LabelCommentsInstructions(
                    modifier = Modifier.fillMaxWidth()
                )
                LabelCommentsSection(
                    modifier = Modifier
                        .padding(mediumPadding)
                        .fillMaxWidth(),
                    numberOfCommentsToLabel = viewState.numberOfCommentsToLabel,
                    onGoToCommentLabelingClicked = onGoToCommentLabelingClicked,
                    onNumberOfCommentsToLabelUpdated = onNumberOfCommentsToLabelUpdated,
                    selectionRange = MIN_COMMENT_QUANTITY..MAX_COMMENT_QUANTITY,
                )
            }
        }
    }
}
