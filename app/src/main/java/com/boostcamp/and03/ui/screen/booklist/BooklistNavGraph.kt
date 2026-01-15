package com.boostcamp.and03.ui.screen.booklist

import android.util.Log
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.boostcamp.and03.ui.navigation.Route
import com.boostcamp.and03.ui.screen.booklist.model.BookUiModel

fun NavGraphBuilder.booklistNavGraph(
    onShowSnackBar: (message: String, actionLabel: String) -> Unit,
    navigateToBookDetail: (String) -> Unit,
    navigateToBookSearch: () -> Unit,
    modifier: Modifier = Modifier,
) {
    composable<Route.Booklist> {
        BooklistRoute(
            onBookClick = { bookId ->
                navigateToBookDetail(bookId)
            },
            onAddBookClick = navigateToBookSearch
        )
    }
}