package com.boostcamp.and03.ui.screen.bookdetail

import com.boostcamp.and03.ui.screen.bookdetail.model.CharacterUiModel
import com.boostcamp.and03.ui.screen.bookdetail.model.MemoUiModel
import com.boostcamp.and03.ui.screen.bookdetail.model.QuoteUiModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

enum class LoadState {
    IDLE,
    LOADING,
    ERROR
}

data class BookDetailUiState(
    val bookId: String = "",
    val thumbnail: String = "",
    val title: String = "",
    val author: String = "",
    val publisher: String = "",
    val totalPage: Int = 0,
    val bookInfoLoadState: LoadState = LoadState.LOADING,

    val characters: ImmutableList<CharacterUiModel> = persistentListOf(),
    val charactersLoadState: LoadState = LoadState.LOADING,

    val quotes: ImmutableList<QuoteUiModel> = persistentListOf(),
    val quotesLoadState: LoadState = LoadState.LOADING,

    val memos: ImmutableList<MemoUiModel> = persistentListOf(),
    val memosLoadState: LoadState = LoadState.LOADING
)