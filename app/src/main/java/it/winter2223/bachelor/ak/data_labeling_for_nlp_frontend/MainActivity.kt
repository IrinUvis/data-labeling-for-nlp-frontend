package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.core.view.WindowCompat
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.commentlabeling.ui.CommentLabelingScreen
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.commentlabeling.ui.CommentLabelingViewModel
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.core.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            DataLabelingForNlpApp()
        }
    }
}

@Composable
fun DataLabelingForNlpApp() {
    AppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
        ) {
            CommentLabelingScreen(CommentLabelingViewModel())
        }
    }
}
