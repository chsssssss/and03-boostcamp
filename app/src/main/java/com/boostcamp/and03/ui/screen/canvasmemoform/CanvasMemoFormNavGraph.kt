package com.boostcamp.and03.ui.screen.canvasmemoform

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.boostcamp.and03.ui.navigation.Route

fun NavGraphBuilder.canvasMemoFormNavGraph(
    navigateToBack: () -> Unit,
    navigateToCanvasMemo: () -> Unit,
    modifier: Modifier = Modifier
) {
    composable<Route.CanvasMemoForm> {
        CanvasMemoFormRoute(
            navigateBack = navigateToBack,
            navigateToCanvasMemo = navigateToCanvasMemo
        )
    }
}