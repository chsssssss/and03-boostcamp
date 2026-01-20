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
            Route.Canvas(memoId = memoId)
        )
    }

    fun navigateToMemoEdit() {
        navController.navigate(Route.MemoEdit)
    }

    fun navigateToAddTextMemo() {
        navController.navigate(Route.AddTextMemo)
    }

    fun navigateToAddCanvasMemo() {
        navController.navigate(Route.AddCanvasMemo)
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