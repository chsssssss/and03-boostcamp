package com.boostcamp.and03.ui.screen.bookdetail

import com.boostcamp.and03.ui.screen.bookdetail.model.CharacterUiModel
import com.boostcamp.and03.ui.screen.bookdetail.model.QuoteUiModel

data class BookDetailUiState(
    val thumbnail: String = "",
    val title: String = "",
    val author: String = "",
    val publisher: String = "",
    val characters: List<CharacterUiModel> = emptyList(),
    val quotes: List<QuoteUiModel> = emptyList(),
)