package it.nlp.frontend.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navOptions
import it.nlp.frontend.ui.textslabeling.TextsLabelingScreen

const val TEXTS_QUANTITY_ARG = "textsQuantity"

object TextsLabelingDestination : AppDestination("textslabeling/{$TEXTS_QUANTITY_ARG}")

fun NavGraphBuilder.textsLabelingScreen(
    navController: NavController,
) {
    composable(
        TextsLabelingDestination.route,
        arguments = listOf(navArgument(TEXTS_QUANTITY_ARG) { type = NavType.IntType })
    ) {
        TextsLabelingScreen(
            navigateUp = {
                navController.navigateToHome {
                    popUpToTop()
                }
            },
            navigateToLogIn = {
                navController.navigateToLogIn {
                    popUpToTop()
                }
            }
        )
    }
}

fun NavController.navigateToTextsLabeling(
    textsQuantity: Int,
    navOptions: NavOptions? = null,
) {
    navigate(
        TextsLabelingDestination.route.replace(
            oldValue = "{$TEXTS_QUANTITY_ARG}",
            newValue = textsQuantity.toString(),
        ),
        navOptions = navOptions
    )
}

fun NavController.navigateToTextsLabeling(
    textsQuantity: Int,
    builder: (NavOptionsBuilder.() -> Unit),
) {
    navigateToTextsLabeling(
        textsQuantity = textsQuantity,
        navOptions = navOptions(builder)
    )
}
