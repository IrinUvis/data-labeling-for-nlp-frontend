package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.core.ui.theme.AppTheme
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.navigation.AppNavHost

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            DataLabelingForNlp()
        }
    }
}

@Composable
fun DataLabelingForNlp() {
    AppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
        ) {
            val navController = rememberNavController()

            AppNavHost(
                navController = navController,
            )
        }
    }
}
