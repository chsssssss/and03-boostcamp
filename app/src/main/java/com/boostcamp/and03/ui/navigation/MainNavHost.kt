package com.boostcamp.and03.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.boostcamp.and03.ui.screen.addbook.addBookNavGraph
import com.boostcamp.and03.ui.screen.bookdetail.bookDetailNavGraph
import com.boostcamp.and03.ui.screen.booklist.booklistNavGraph
import com.boostcamp.and03.ui.screen.booksearch.bookSearchNavGraph
import com.boostcamp.and03.ui.screen.canvasmemo.canvasMemoNavGraph
import com.boostcamp.and03.ui.screen.canvasmemoform.canvasMemoFormNavGraph
import com.boostcamp.and03.ui.screen.characterform.characterFormNavGraph
import com.boostcamp.and03.ui.screen.mypage.myPageNavGraph
import com.boostcamp.and03.ui.screen.quoteform.quoteFormNavGraph
import com.boostcamp.and03.ui.screen.textmemoform.textMemoFormNavGraph

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
            navigateToCharacterForm = { bookId, characterId ->
                navigator.navigateToCharacterForm(
                    bookId = bookId,
                    characterId = characterId
                )
            },
            navigateToQuoteForm = { bookId, quoteId, totalPage ->
                navigator.navigateToQuoteForm(
                    bookId = bookId,
                    quoteId = quoteId,
                    totalPage = totalPage
                )
            },
            navigateToTextMemoForm = { bookId, memoId, totalPage ->
                navigator.navigateToTextMemoForm(
                    bookId = bookId,
                    memoId = memoId,
                    totalPage = totalPage
                )
            },
            navigateToCanvasMemoForm = { bookId, memoId, totalPage ->
                navigator.navigateToCanvasMemoForm(
                    bookId = bookId,
                    memoId = memoId,
                    totalPage = totalPage
                )
            },
            navigateToCanvas = { bookId, memoId ->
                navigator.navigateToCanvas(
                    bookId = bookId,
                    memoId = memoId
                )
            },
        )

        textMemoFormNavGraph(
            navigateBack = { navigator.navigatePopBackStack() },
            modifier = modifier.padding(paddingValues)
        )

        canvasMemoFormNavGraph(
            navigateToBack = { navigator.navigatePopBackStack() },
            navigateToCanvasMemo = { navigator.navigateToCanvasMemo() },
            modifier = modifier.padding(paddingValues)
        )

        characterFormNavGraph(
            navigateBack = { navigator.navigatePopBackStack() },
            modifier = modifier.padding(paddingValues)
        )

        quoteFormNavGraph(
            navigateBack = { navigator.navigatePopBackStack() },
            modifier = modifier.padding(paddingValues)
        )

        canvasMemoNavGraph(
            navigateToBack = { navigator.navigatePopBackStack() },
            modifier = modifier.padding(paddingValues)
        )
    }
}