package com.boostcamp.and03.data.datasource.remote.memo

import com.boostcamp.and03.data.model.request.CanvasMemoRequest
import com.boostcamp.and03.data.model.request.TextMemoRequest
import com.boostcamp.and03.data.model.response.memo.CanvasMemoResponse
import com.boostcamp.and03.data.model.response.memo.MemoResponse
import com.boostcamp.and03.data.model.response.memo.TextMemoResponse
import kotlinx.coroutines.flow.Flow

interface MemoDataSource {

    suspend fun getMemos(
        userId: String,
        bookId: String
    ): Flow<List<MemoResponse>>

    suspend fun addTextMemo(
        userId: String,
        bookId: String,
        memo: TextMemoRequest
    )

    suspend fun addCanvasMemo(
        userId: String,
        bookId: String,
        memo: CanvasMemoRequest
    )

    suspend fun updateTextMemo(
        userId: String,
        bookId: String,
        memoId: String,
        memo: TextMemoRequest
    )

    suspend fun updateCanvasMemo(
        userId: String,
        bookId: String,
        memoId: String,
        memo: CanvasMemoRequest
    )

    suspend fun deleteMemo(
        userId: String,
        bookId: String,
        memoId: String
    )

    suspend fun getTextMemo(
        userId: String,
        bookId: String,
        memoId: String
    ): TextMemoResponse

    suspend fun getCanvasMemo(
        userId: String,
        bookId: String,
        memoId: String
    ): CanvasMemoResponse
}