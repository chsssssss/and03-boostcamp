package com.boostcamp.and03.ui.screen.quoteform

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.boostcamp.and03.ui.navigation.Route

fun NavGraphBuilder.quoteFormNavGraph(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    composable<Route.QuoteForm>{
        QuoteFormRoute(
            navigateBack = navigateBack
        )
    }
}