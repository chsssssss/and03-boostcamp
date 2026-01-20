package com.boostcamp.and03.ui.screen.canvasmemo

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.boostcamp.and03.ui.navigation.Route

fun NavGraphBuilder.canvasMemoNavGraph(
    navigateToBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    composable<Route.CanvasMemo> {
        CanvasMemoRoute(
            navigateToBack = navigateToBack
        )
    }
}