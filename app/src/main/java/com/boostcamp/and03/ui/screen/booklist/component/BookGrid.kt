package com.boostcamp.and03.ui.screen.booklist.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import com.boostcamp.and03.ui.screen.booklist.model.BookUIModel
import com.boostcamp.and03.ui.theme.Dimensions

@Composable
fun BookGrid(
    books: List<BookUIModel>,
    onBookClick: (BookUIModel) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(Dimensions.PADDING_L),
        horizontalArrangement = Arrangement.spacedBy(Dimensions.PADDING_M),
        contentPadding = PaddingValues(
            bottom = Dimensions.PADDING_L
        )
    ) {
        items(books) { book ->
            BookItem(
                title = book.title,
                authors = book.authors,
                thumbnail = book.thumbnail,
                onClick = { onBookClick(book) }
            )
        }
    }
}
