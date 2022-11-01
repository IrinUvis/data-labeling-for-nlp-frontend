package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.commentlabeling.ui.CommentLabelingScreen
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.core.ui.theme.DataLabelingForNLPFrontendTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DataLabelingForNLPFrontendTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    CommentLabelingScreen()
                }
            }
        }
    }
}
