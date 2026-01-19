package com.boostcamp.and03.ui.screen.textmemoeditor

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.boostcamp.and03.ui.navigation.Route

fun NavGraphBuilder.textMemoEditorNavGraph(
    navigateToBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    composable<Route.AddTextMemo> {
        TextMemoEditorRoute(
            navigateBack = navigateToBack
        )
    }

    composable<Route.EditTextMemo> {
        TextMemoEditorRoute(
            navigateBack = navigateToBack
        )
    }
}