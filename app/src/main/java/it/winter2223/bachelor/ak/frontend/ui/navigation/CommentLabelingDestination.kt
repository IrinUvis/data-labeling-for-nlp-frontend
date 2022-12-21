package it.winter2223.bachelor.ak.frontend.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navOptions
import it.winter2223.bachelor.ak.frontend.ui.commentlabeling.CommentLabelingScreen

private const val COMMENT_QUANTITY_ARG = "commentQuantity"

object CommentLabelingDestination : AppDestination("commentlabeling/{$COMMENT_QUANTITY_ARG}")

fun NavGraphBuilder.commentLabelingScreen(
    navController: NavController,
) {
    composable(
        CommentLabelingDestination.route,
        arguments = listOf(navArgument(COMMENT_QUANTITY_ARG) { type = NavType.IntType })
    ) {
        CommentLabelingScreen(
            navigateUp = { navController.navigateUp() },
            navigateToLogIn = {
                navController.navigateToLogIn {
                    popUpToTop()
                }
            }
        )
    }
}

fun NavController.navigateToCommentLabeling(
    commentQuantity: Int,
    navOptions: NavOptions? = null,
) {
    navigate(
        CommentLabelingDestination.route.replace(
            oldValue = "{$COMMENT_QUANTITY_ARG}",
            newValue = commentQuantity.toString(),
        ),
        navOptions = navOptions
    )
}

fun NavController.navigateToCommentLabeling(
    commentQuantity: Int,
    builder: (NavOptionsBuilder.() -> Unit),
) {
    navigateToCommentLabeling(
        commentQuantity = commentQuantity,
        navOptions = navOptions(builder)
    )
}
