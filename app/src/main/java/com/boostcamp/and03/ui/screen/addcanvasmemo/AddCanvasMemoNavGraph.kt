package com.boostcamp.and03.ui.screen.addcanvasmemo

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.boostcamp.and03.ui.navigation.Route

fun NavGraphBuilder.addCanvasMemoNavGraph(
    navigateToBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    composable<Route.AddCanvasMemo> {
        AddCanvasMemoRoute(
            navigateToBack = navigateToBack
        )
    }
}