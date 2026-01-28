package com.boostcamp.and03.ui.screen.bookdetail

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.boostcamp.and03.ui.navigation.Route

fun NavGraphBuilder.bookDetailNavGraph(
    modifier: Modifier = Modifier,
    navigateToBack: () -> Unit,
    navigateToCharacterForm: (bookId: String, characterId: String) -> Unit,
    navigateToQuoteForm: (bookId: String, quoteId: String, totalPage: Int) -> Unit,
    navigateToTextMemoForm: (bookId: String, memoId: String, totalPage: Int) -> Unit,
    navigateToCanvasMemoForm: (bookId: String, memoId: String, totalPage: Int) -> Unit,
    navigateToCanvas: (String) -> Unit,
) {
    composable<Route.BookDetail> {
        BookDetailRoute(
            navigateBack = navigateToBack,
            navigateToCharacterForm = navigateToCharacterForm,
            navigateToQuoteForm = navigateToQuoteForm,
            navigateToTextMemoForm = navigateToTextMemoForm,
            navigateToCanvasMemoForm = navigateToCanvasMemoForm,
            navigateToCanvas = navigateToCanvas,
        )
    }
}