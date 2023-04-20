@file:Suppress("UnusedPrivateMember")

package it.nlp.frontend.ui.textslabeling.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import it.nlp.frontend.R
import it.nlp.frontend.ui.core.component.NavigateUpButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextsLabelingTopBar(
    modifier: Modifier = Modifier,
    onBackButtonClicked: () -> Unit,
) {
    TopAppBar(
        modifier = modifier,
        title = { Text(text = stringResource(R.string.textsLabelingAppBarTitle)) },
        navigationIcon = { NavigateUpButton(onClick = onBackButtonClicked) },
    )
}
