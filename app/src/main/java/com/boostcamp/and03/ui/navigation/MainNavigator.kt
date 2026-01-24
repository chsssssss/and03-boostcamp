package com.boostcamp.and03.ui.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Stable
class MainNavigator(
    val navController: NavHostController
) {
    val startDestination = Route.Booklist

    fun navigateToBooklist() {
        navController.navigate(Route.Booklist)
    }

    fun navigateToBookSearch() {
        navController.navigate(Route.BookSearch)
    }

    fun navigateToAddBook() {
        navController.navigate(Route.AddBook)
    }

    fun navigateToMyPage() {
        navController.navigate(Route.MyPage)
    }

    fun navigateToBookDetail(bookId: String) {
        navController.navigate(
            Route.BookDetail(bookId = bookId)
        )
    }

    fun navigateToCanvas(memoId: String) {
        navController.navigate(
            Route.CanvasMemo(memoId = memoId)
        )
    }

    fun navigateToCharacterForm(
        bookId: String,
        characterId: String
    ) {
        navController.navigate(
            Route.CharacterForm(
                bookId = bookId,
                characterId = characterId
            )
        )
    }

    fun navigateToQuoteForm(
        bookId: String,
        quoteId: String
    ) {
        navController.navigate(
            Route.QuoteForm(
                bookId = bookId,
                quoteId = quoteId
            )
        )
    }

    fun navigateToTextMemoForm(
        bookId: String,
        memoId: String
    ) {
        navController.navigate(
            Route.TextMemoForm(
                bookId = bookId,
                memoId = memoId
            )
        )
    }

    fun navigateToCanvasMemoForm(
        bookId: String,
        memoId: String
    )  {
        navController.navigate(
            Route.CanvasMemoForm(
                bookId = bookId,
                memoId = memoId
            )
        )
    }

    fun navigateToCanvasMemo() {
        navController.navigate(Route.CanvasMemo)
    }

    fun navigatePopBackStack() = navController.popBackStack()
}

@SuppressLint("ComposableNaming")
@Composable
fun rememberMainNavigator(
    navController: NavHostController = rememberNavController()
): MainNavigator = remember(navController) {
    MainNavigator(navController)
}