package com.boostcamp.and03.ui.screen.bookdetail

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.boostcamp.and03.ui.navigation.Route

fun NavGraphBuilder.bookDetailNavGraph(
    modifier: Modifier = Modifier,
    navigateToBack: () -> Unit,
) {
    composable<Route.BookDetail> { backStackEntry ->
        val route = backStackEntry.toRoute<Route.BookDetail>()
        BookDetailRoute(
            navigateToBack = navigateToBack,
            isbn = route.isbn
        )
    }
}