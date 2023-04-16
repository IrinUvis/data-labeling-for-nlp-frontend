package it.nlp.frontend.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import it.nlp.frontend.ui.core.model.UiTheme
import it.nlp.frontend.ui.core.theme.AppTheme
import it.nlp.frontend.ui.navigation.AppNavHost
import it.nlp.frontend.ui.splash.SplashViewModel
import it.nlp.frontend.ui.splash.SplashViewState

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashViewModel = ViewModelProvider(this)[SplashViewModel::class.java]

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                splashViewModel.viewState.value is SplashViewState.Loading
            }
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
    val splashState = splashViewModel.viewState.value

    val darkTheme = when ((splashState as? SplashViewState.Completed)?.theme ?: UiTheme.System) {
        UiTheme.Light -> false
        UiTheme.Dark -> true
        UiTheme.System -> isSystemInDarkTheme()
    }

    AppTheme(
        darkTheme = darkTheme
    ) {
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
