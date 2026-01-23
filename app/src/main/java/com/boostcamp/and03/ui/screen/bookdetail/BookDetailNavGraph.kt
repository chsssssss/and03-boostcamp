package com.boostcamp.and03.ui.screen.bookdetail

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.boostcamp.and03.ui.navigation.Route
import com.boostcamp.and03.ui.screen.prototype.screen.CanvasScreen
import com.boostcamp.and03.ui.screen.prototype.screen.MemoEditScreen

fun NavGraphBuilder.bookDetailNavGraph(
    modifier: Modifier = Modifier,
    navigateToBack: () -> Unit,
    navigateToCharacterForm: (bookId: String, characterId: String) -> Unit,
    navigateToQuoteForm: (bookId: String, quoteId: String) -> Unit,
    navigateToTextMemoForm: (bookId: String, memoId: String) -> Unit,
    navigateToCanvasMemoForm: (bookId: String, memoId: String) -> Unit,
    navigateToCanvas: (String) -> Unit,
    navigateToMemoEdit: (String) -> Unit,
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

    composable<Route.Canvas> { backStackEntry ->
        val route = backStackEntry.toRoute<Route.Canvas>()
        CanvasScreen(
            memoId = route.memoId,
            navigateToBack = navigateToBack,
            navigateToMemoEdit = {
                navigateToMemoEdit(route.memoId)
            }
        )
    }

    composable<Route.MemoEdit> { backStackEntry ->
        val route = backStackEntry.toRoute<Route.MemoEdit>()
        MemoEditScreen(
            onBack = navigateToBack,
            onSave = { title, content -> /* 저장 */ }
        )
    }
}