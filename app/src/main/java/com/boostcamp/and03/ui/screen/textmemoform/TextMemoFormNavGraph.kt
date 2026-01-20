package com.boostcamp.and03.ui.screen.textmemoform

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.boostcamp.and03.ui.navigation.Route

fun NavGraphBuilder.textMemoFormNavGraph(
    navigateToBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    composable<Route.TextMemoForm> {
        TextMemoFormRoute(
            navigateBack = navigateToBack
        )
    }
}