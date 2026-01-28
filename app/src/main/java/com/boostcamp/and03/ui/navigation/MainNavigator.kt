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

    fun navigateToCanvas(
        bookId: String,
        memoId: String,
        totalPage: Int
    ) {
        navController.navigate(
            Route.CanvasMemo(
                bookId = bookId,
                memoId = memoId,
                totalPage = totalPage
            )
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
        quoteId: String,
        totalPage: Int
    ) {
        navController.navigate(
            Route.QuoteForm(
                bookId = bookId,
                quoteId = quoteId,
                totalPage = totalPage
            )
        )
    }

    fun navigateToTextMemoForm(
        bookId: String,
        memoId: String,
        totalPage: Int
    ) {
        navController.navigate(
            Route.TextMemoForm(
                bookId = bookId,
                memoId = memoId,
                totalPage = totalPage
            )
        )
    }

    fun navigateToCanvasMemoForm(
        bookId: String,
        memoId: String,
        totalPage: Int
    ) {
        navController.navigate(
            Route.CanvasMemoForm(
                bookId = bookId,
                memoId = memoId,
                totalPage = totalPage
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