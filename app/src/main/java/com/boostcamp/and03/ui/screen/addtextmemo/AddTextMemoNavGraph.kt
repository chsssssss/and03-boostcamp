package com.boostcamp.and03.ui.screen.addtextmemo

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.boostcamp.and03.ui.navigation.Route

fun NavGraphBuilder.addTextMemoNavGraph(
    navigateToBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    composable<Route.AddTextMemo> {
        AddTextMemoRoute(
            navigateToBack = navigateToBack
        )
    }
}