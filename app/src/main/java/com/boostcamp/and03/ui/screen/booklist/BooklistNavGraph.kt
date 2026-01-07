package com.boostcamp.and03.ui.screen.booklist

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.boostcamp.and03.ui.navigation.Route

fun NavGraphBuilder.booklistNavGraph(
    onShowSnackBar: (message: String, actionLabel: String) -> Unit,
    navigateToBookDetail: () -> Unit,
    navigateToBookSearch: () -> Unit,
    modifier: Modifier = Modifier,
) {
    composable<Route.Booklist> {
        BooklistRoute(
            onBookClick = { navigateToBookDetail() },
            onAddBookClick = navigateToBookSearch
        )
    }
}