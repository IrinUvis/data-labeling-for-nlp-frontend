package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.core.ui.theme.AppTheme
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.navigation.AppDestination
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.navigation.AppNavHost
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.navigation.LogInDestination
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.splash.ui.SplashViewModel
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.splash.ui.SplashViewState
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashViewModel = ViewModelProvider(this)[SplashViewModel::class.java]

        installSplashScreen().setKeepOnScreenCondition {
            splashViewModel.viewState.value is SplashViewState.Loading
        }

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            Log.d("MAINACTIVITY", splashViewModel.viewState.value.startDestination.route)

            DataLabelingForNlp(
                startDestination = splashViewModel.viewState.value.startDestination
            )
        }
    }
}

@Composable
fun DataLabelingForNlp(
    startDestination: AppDestination,
) {
    AppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
        ) {
            val navController = rememberNavController()

            AppNavHost(
                navController = navController,
                startDestination = startDestination,
            )
        }
    }
}
