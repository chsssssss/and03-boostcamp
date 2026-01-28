package com.boostcamp.and03.data.repository.bookstorage

import com.boostcamp.and03.data.model.request.BookStorageRequest
import com.boostcamp.and03.data.model.response.BookDetailResponse
import com.boostcamp.and03.data.model.response.BookStorageResponse
import com.boostcamp.and03.data.model.response.CharacterResponse
import com.boostcamp.and03.data.model.response.QuoteResponse
import com.boostcamp.and03.data.model.response.memo.CanvasMemoResponse
import com.boostcamp.and03.data.model.response.memo.MemoResponse
import com.boostcamp.and03.data.model.response.memo.TextMemoResponse
import com.boostcamp.and03.ui.screen.bookdetail.model.CharacterUiModel
import com.boostcamp.and03.ui.screen.bookdetail.model.QuoteUiModel
import com.boostcamp.and03.ui.screen.canvasmemoform.model.CanvasMemoFormUiModel
import com.boostcamp.and03.ui.screen.textmemoform.model.TextMemoFormUiModel
import kotlinx.coroutines.flow.Flow

interface BookStorageRepository {
    suspend fun getBooks(userId: String): List<BookStorageResponse>

    suspend fun getBookDetail(
        userId: String,
        bookId: String
    ): BookDetailResponse?

    suspend fun saveBook(
        userId: String,
        book: BookStorageRequest
    )

    suspend fun getCharacters(
        userId: String,
        bookId: String
    ): Flow<List<CharacterResponse>>

    suspend fun getCharacter(
        userId: String,
        bookId: String,
        characterId: String
    ): CharacterResponse

    suspend fun addCharacter(
        userId: String,
        bookId: String,
        character: CharacterUiModel
    )

    suspend fun deleteCharacter(
        userId: String,
        bookId: String,
        characterId: String
    )

    suspend fun updateCharacter(
        userId: String,
        bookId: String,
        characterId: String,
        character: CharacterUiModel
    )

    suspend fun getQuotes(
        userId: String,
        bookId: String
    ): Flow<List<QuoteResponse>>

    suspend fun getQuote(
        userId: String,
        bookId: String,
        quoteId: String
    ): QuoteResponse

    suspend fun addQuote(
        userId: String,
        bookId: String,
        quote: QuoteUiModel
    )

    suspend fun updateQuote(
        userId: String,
        bookId: String,
        quoteId: String,
        quote: QuoteUiModel
    )

    suspend fun deleteQuote(
        userId: String,
        bookId: String,
        quoteId: String
    )

    suspend fun getMemos(
        userId: String,
        bookId: String
    ): Flow<List<MemoResponse>>

    suspend fun addTextMemo(
        userId: String,
        bookId: String,
        memo: TextMemoFormUiModel
    )

    suspend fun getTextMemo(
        userId: String,
        bookId: String,
        memoId: String
    ): TextMemoResponse

    suspend fun addCanvasMemo(
        userId: String,
        bookId: String,
        memo: CanvasMemoFormUiModel
    )

    suspend fun updateTextMemo(
        userId: String,
        bookId: String,
        memoId: String,
        memo: TextMemoFormUiModel
    )

    suspend fun updateCanvasMemo(
        userId: String,
        bookId: String,
        memoId: String,
        memo: CanvasMemoFormUiModel
    )

    suspend fun deleteMemo(
        userId: String,
        bookId: String,
        memoId: String
    )

    suspend fun getCanvasMemo(
        userId: String,
        bookId: String,
        memoId: String
    ): CanvasMemoResponse
}