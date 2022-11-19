package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.home.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.core.ui.mediumPadding
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.core.ui.smallPadding
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.home.ui.components.HomeTopBar
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.home.ui.components.LabelCommentsInstructions
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.home.ui.components.LabelCommentsSection

private const val MIN_COMMENT_QUANTITY = 5
private const val MAX_COMMENT_QUANTITY = 20

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
    viewState: HomeViewState.Loaded,
    onLogOutButtonClicked: () -> Unit,
    onGoToCommentLabelingClicked: () -> Unit,
    onNumberOfCommentsToLabelUpdated: (Int) -> Unit,
) {
    Scaffold(
        topBar = {
            HomeTopBar(
                onLogOutButtonClicked = onLogOutButtonClicked,
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
                LabelCommentsInstructions()
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
