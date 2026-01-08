package com.boostcamp.and03.ui.screen.bookdetail

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.boostcamp.and03.ui.navigation.MainTabRoute

fun NavGraphBuilder.bookDetailNavGraph(
    modifier: Modifier = Modifier,
    navigateToBack: () -> Unit,
) {
    composable<MainTabRoute.BookDetail> {
        BookDetailRoute(
            navigateToBack = navigateToBack
        )
    }
}