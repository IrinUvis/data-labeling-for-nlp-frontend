package it.winter2223.bachelor.ak.frontend.ui

import android.os.Bundle
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
import it.winter2223.bachelor.ak.frontend.ui.core.theme.AppTheme
import it.winter2223.bachelor.ak.frontend.ui.navigation.AppNavHost
import it.winter2223.bachelor.ak.frontend.ui.splash.SplashViewModel
import it.winter2223.bachelor.ak.frontend.ui.splash.SplashViewState

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
            DataLabelingForNlp(
                splashViewModel = splashViewModel,
            )
        }
    }
}

@Composable
fun DataLabelingForNlp(
    splashViewModel: SplashViewModel,
) {
    AppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
        ) {
            val navController = rememberNavController()

            AppNavHost(
                navController = navController,
                splashViewModel = splashViewModel,
            )
        }
    }
}