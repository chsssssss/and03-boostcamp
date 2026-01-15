package com.boostcamp.and03.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.boostcamp.and03.ui.screen.addbook.addBookNavGraph
import com.boostcamp.and03.ui.screen.addcanvasmemo.addCanvasMemoNavGraph
import com.boostcamp.and03.ui.screen.addtextmemo.addTextMemoNavGraph
import com.boostcamp.and03.ui.screen.bookdetail.bookDetailNavGraph
import com.boostcamp.and03.ui.screen.booklist.booklistNavGraph
import com.boostcamp.and03.ui.screen.booksearch.bookSearchNavGraph
import com.boostcamp.and03.ui.screen.mypage.myPageNavGraph

@Composable
fun MainNavHost(
    navigator: MainNavigator,
    paddingValues: PaddingValues,
    onShowSnackBar: (message: String, actionLabel: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navigator.navController,
        startDestination = navigator.startDestination
    ) {
        booklistNavGraph(
            modifier = modifier.padding(paddingValues),
            onShowSnackBar = onShowSnackBar,
            navigateToBookDetail = { bookId ->
                navigator.navigateToBookDetail(bookId)
            },
            navigateToBookSearch = {
                navigator.navigateToBookSearch()
            }
        )

        bookSearchNavGraph(
            navigator = navigator,
            modifier = modifier.padding(paddingValues)
        )

        addBookNavGraph(modifier = modifier.padding(paddingValues))

        myPageNavGraph(modifier = modifier.padding(paddingValues))

        bookDetailNavGraph(
            navigateToBack = { navigator.navigatePopBackStack() },
            navigateToCanvas = { memoId ->
                navigator.navigateToCanvas(memoId)
            },
            navigateToAddTextMemo = { navigator.navigateToAddTextMemo() },
            navigateToAddCanvasMemo = { navigator.navigateToAddCanvasMemo() },
            navigateToMemoEdit = { navigator.navigateToMemoEdit() }
        )

        addTextMemoNavGraph(
            navigateToBack = { navigator.navigatePopBackStack() },
            modifier = modifier.padding(paddingValues)
        )

        addCanvasMemoNavGraph(
            navigateToBack = { navigator.navigatePopBackStack() },
            modifier = modifier.padding(paddingValues)
            navigateToMemoEdit = {
                navigator.navigateToMemoEdit()
            }
        )
    }
}