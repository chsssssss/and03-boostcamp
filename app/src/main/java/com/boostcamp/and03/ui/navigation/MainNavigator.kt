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

    /**
     * memoId == "" : 새 메모 생성 모드
     * memoId != "" : 기존 메모 수정 모드
     */
    fun navigateToTextMemoEditor(
        bookId: String,
        memoId: String = ""
    ) {
        navController.navigate(
            Route.TextMemoEditor(
                bookId = bookId,
                memoId = memoId
            )
        )
    }

    /**
     * memoId == "" : 새 메모 생성 모드
     * memoId != "" : 기존 메모 수정 모드
     */
    fun navigateToCanvasMemoEditor(
        bookId: String,
        memoId: String = ""
    )  {
        navController.navigate(
            Route.CanvasMemoEditor(
                bookId = bookId,
                memoId = memoId
            )
        )
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