@file:OptIn(ExperimentalMaterial3Api::class)

package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.ui.theme.DataLabelingForNLPFrontendTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            DataLabelingForNLPFrontendTheme {
                Scaffold { paddingValues ->
                    Text(
                        modifier = Modifier.padding(paddingValues),
                        text = "Hello Droid!",
                    )
                }
            }
        }
    }
}
