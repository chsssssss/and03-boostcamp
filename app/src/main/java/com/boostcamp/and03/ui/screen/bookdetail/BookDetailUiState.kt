package com.boostcamp.and03.ui.screen.bookdetail

import com.boostcamp.and03.ui.screen.bookdetail.model.CharacterUiModel
import com.boostcamp.and03.ui.screen.bookdetail.model.MemoUiModel
import com.boostcamp.and03.ui.screen.bookdetail.model.QuoteUiModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class BookDetailUiState(
    val bookId: String = "",
    val thumbnail: String = "",
    val title: String = "",
    val author: String = "",
    val publisher: String = "",
    val totalPage: Int = 0,
    val characters: ImmutableList<CharacterUiModel> = persistentListOf(),
    val quotes: ImmutableList<QuoteUiModel> = persistentListOf(),
    val memos: ImmutableList<MemoUiModel> = persistentListOf(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)