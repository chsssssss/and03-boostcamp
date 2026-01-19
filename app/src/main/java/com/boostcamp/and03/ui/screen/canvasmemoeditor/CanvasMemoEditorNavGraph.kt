package com.boostcamp.and03.ui.screen.canvasmemoeditor

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.boostcamp.and03.ui.navigation.Route

fun NavGraphBuilder.canvasMemoEditorNavGraph(
    navigateToBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    composable<Route.AddCanvasMemo> {
        CanvasMemoEditorRoute(
            navigateBack = navigateToBack
        )
    }

    composable<Route.EditCanvasMemo> {
        CanvasMemoEditorRoute(
            navigateBack = navigateToBack
        )
    }
}