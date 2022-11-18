package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.home.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.core.ui.components.HorizontalSpacer
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.core.ui.components.NumberPicker
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.core.ui.mediumPadding
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.core.ui.smallPadding
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.home.ui.components.HomeTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
    viewState: HomeViewState,
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
        when (viewState) {
            is HomeViewState.Loading -> {
                Box(modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
            is HomeViewState.Loaded -> {
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
                    Row(
                        modifier = Modifier
                            .padding(mediumPadding)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Button(modifier = Modifier,
                            onClick = onGoToCommentLabelingClicked) {
                            Text(text = "Label comments")
                        }
                        HorizontalSpacer(width = mediumPadding)
                        NumberPicker(
                            modifier = Modifier
                                .animateContentSize(),
                            value = viewState.numberOfCommentsToLabel,
                            onValueChange = onNumberOfCommentsToLabelUpdated,
                            range = 1..20,
                        )
                    }
                }
            }
        }
    }
}