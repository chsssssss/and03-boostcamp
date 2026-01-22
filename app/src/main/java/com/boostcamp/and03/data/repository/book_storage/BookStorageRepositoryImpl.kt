package com.boostcamp.and03.data.repository.book_storage

import com.boostcamp.and03.data.datasource.remote.book_storage.BookStorageDataSource
import com.boostcamp.and03.data.datasource.remote.character.CharacterDataSource
import com.boostcamp.and03.data.datasource.remote.memo.MemoDataSource
import com.boostcamp.and03.data.datasource.remote.quote.QuoteDataSource
import com.boostcamp.and03.data.model.request.BookStorageRequest
import com.boostcamp.and03.data.model.request.CanvasMemoRequest
import com.boostcamp.and03.data.model.request.CharacterRequest
import com.boostcamp.and03.data.model.request.QuoteRequest
import com.boostcamp.and03.data.model.request.TextMemoRequest
import com.boostcamp.and03.data.model.request.toRequest
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
import javax.inject.Inject

class BookStorageRepositoryImpl @Inject constructor(
    private val bookStorageDataSource: BookStorageDataSource,
    private val characterDataSource: CharacterDataSource,
    private val quoteDataSource: QuoteDataSource,
    private val memoDataSource: MemoDataSource
) : BookStorageRepository {
    override suspend fun getBooks(userId: String): List<BookStorageResponse> {
        return bookStorageDataSource.getBooks(userId)
    }

    override suspend fun getBookDetail(
        userId: String,
        bookId: String
    ): BookDetailResponse? {
        return bookStorageDataSource.getBookDetail(
            userId,
            bookId
        )
    }

    override suspend fun saveBook(
        userId: String,
        book: BookStorageRequest
    ) {
        return bookStorageDataSource.saveBook(userId, book)
    }

    override suspend fun getCharacters(
        userId: String,
        bookId: String
    ): List<CharacterResponse> {
        return characterDataSource.getCharacters(userId, bookId)
    }

    override suspend fun getCharacter(
        userId: String,
        bookId: String,
        characterId: String
    ): CharacterResponse {
        return characterDataSource.getCharacter(userId, bookId, characterId)
    }

    override suspend fun addCharacter(
        userId: String,
        bookId: String,
        character: CharacterRequest
    ) {
        characterDataSource.addCharacter(userId, bookId, character)
    }

    override suspend fun deleteCharacter(
        userId: String,
        bookId: String,
        characterId: String
    ) {
        characterDataSource.deleteCharacter(userId, bookId, characterId)
    }

    override suspend fun updateCharacter(
        userId: String,
        bookId: String,
        characterId: String,
        character: CharacterUiModel
    ) {
        characterDataSource.updateCharacter(
            userId,
            bookId,
            characterId,
            character.toRequest()
        )
    }

    override suspend fun getQuotes(
        userId: String,
        bookId: String
    ): List<QuoteResponse> {
        return quoteDataSource.getQuotes(userId, bookId)
    }

    override suspend fun addQuote(
        userId: String,
        bookId: String,
        quote: QuoteRequest
    ) {
        quoteDataSource.addQuote(userId, bookId, quote)
    }

    override suspend fun deleteQuote(
        userId: String,
        bookId: String,
        quoteId: String
    ) {
        quoteDataSource.deleteQuote(
            userId,
            bookId,
            quoteId
        )
    }

    override suspend fun getMemos(
        userId: String,
        bookId: String
    ): List<MemoResponse> {
        return memoDataSource.getMemos(
            userId,
            bookId
        )
    }

    override suspend fun addTextMemo(
        userId: String,
        bookId: String,
        memo: TextMemoFormUiModel
    ) {
        memoDataSource.addTextMemo(
            userId,
            bookId,
            memo.toRequest()
        )
    }

    override suspend fun getTextMemo(
        userId: String,
        bookId: String,
        memoId: String
    ): TextMemoResponse {
        return memoDataSource.getTextMemo(
            userId,
            bookId,
            memoId
        )
    }

    override suspend fun addCanvasMemo(
        userId: String,
        bookId: String,
        memo: CanvasMemoFormUiModel
    ) {
        memoDataSource.addCanvasMemo(
            userId,
            bookId,
            memo.toRequest()
        )
    }

    override suspend fun updateTextMemo(
        userId: String,
        bookId: String,
        memoId: String,
        memo: TextMemoFormUiModel
    ) {
        memoDataSource.updateTextMemo(
            userId,
            bookId,
            memoId,
            memo.toRequest()
        )
    }

    override suspend fun updateCanvasMemo(
        userId: String,
        bookId: String,
        memoId: String,
        memo: CanvasMemoFormUiModel
    ) {
        memoDataSource.updateCanvasMemo(
            userId,
            bookId,
            memoId,
            memo.toRequest()
        )
    }

    override suspend fun deleteMemo(
        userId: String,
        bookId: String,
        memoId: String
    ) {
        memoDataSource.deleteMemo(
            userId,
            bookId,
            memoId
        )
    }

    override suspend fun getCanvasMemo(
        userId: String,
        bookId: String,
        memoId: String
    ): CanvasMemoResponse {
        return memoDataSource.getCanvasMemo(
            userId,
            bookId,
            memoId
        )
    }
}