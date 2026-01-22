package com.boostcamp.and03.data.repository.book_storage

import com.boostcamp.and03.data.model.request.BookStorageRequest
import com.boostcamp.and03.data.model.request.CanvasMemoRequest
import com.boostcamp.and03.data.model.request.CharacterRequest
import com.boostcamp.and03.data.model.request.QuoteRequest
import com.boostcamp.and03.data.model.request.TextMemoRequest
import com.boostcamp.and03.data.model.response.BookDetailResponse
import com.boostcamp.and03.data.model.response.BookStorageResponse
import com.boostcamp.and03.data.model.response.CharacterResponse
import com.boostcamp.and03.data.model.response.QuoteResponse
import com.boostcamp.and03.data.model.response.memo.CanvasMemoResponse
import com.boostcamp.and03.data.model.response.memo.MemoResponse
import com.boostcamp.and03.data.model.response.memo.TextMemoResponse
import com.boostcamp.and03.ui.screen.bookdetail.model.CharacterUiModel
import com.boostcamp.and03.ui.screen.canvasmemoform.model.CanvasMemoFormUiModel
import com.boostcamp.and03.ui.screen.textmemoform.model.TextMemoFormUiModel

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
    ): List<CharacterResponse>

    suspend fun getCharacter(
        userId: String,
        bookId: String,
        characterId: String
    ): CharacterResponse

    suspend fun addCharacter(
        userId: String,
        bookId: String,
        character: CharacterRequest
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
    ): List<QuoteResponse>

    suspend fun addQuote(
        userId: String,
        bookId: String,
        quote: QuoteRequest
    )

    suspend fun deleteQuote(
        userId: String,
        bookId: String,
        quoteId: String
    )

    suspend fun getMemos(
        userId: String,
        bookId: String
    ): List<MemoResponse>

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