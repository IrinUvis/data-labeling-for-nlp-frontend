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
import it.nlp.frontend.ui.home.component.LabelTextsInstructions
import it.nlp.frontend.ui.home.component.LabelTextsSection

private const val MIN_TEXTS_QUANTITY = 5
private const val MAX_TEXTS_QUANTITY = 20

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
    viewState: HomeViewState.Loaded,
    onSettingsButtonClicked: () -> Unit,
    onGoToTextsLabelingClicked: () -> Unit,
    onNumberOfTextsToLabelUpdated: (Int) -> Unit,
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
                LabelTextsInstructions(
                    modifier = Modifier.fillMaxWidth()
                )
                LabelTextsSection(
                    modifier = Modifier
                        .padding(mediumPadding)
                        .fillMaxWidth(),
                    numberOfTextsToLabel = viewState.numberOfTextsToLabel,
                    onGoToTextsLabelingClicked = onGoToTextsLabelingClicked,
                    onNumberOfTextsToLabelUpdated = onNumberOfTextsToLabelUpdated,
                    selectionRange = MIN_TEXTS_QUANTITY..MAX_TEXTS_QUANTITY,
                )
            }
        }
    }
}
