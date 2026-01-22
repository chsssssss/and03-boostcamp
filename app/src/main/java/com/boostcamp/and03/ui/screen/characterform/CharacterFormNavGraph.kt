package com.boostcamp.and03.ui.screen.characterform

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.boostcamp.and03.ui.navigation.Route

fun NavGraphBuilder.characterFormNavGraph(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    composable<Route.CharacterForm> {
        CharacterFormRoute(
            navigateBack = navigateBack
        )
    }
}