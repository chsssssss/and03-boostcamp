package com.boostcamp.and03.ui.screen.bookdetail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.boostcamp.and03.data.repository.book_storage.BookStorageRepository
import com.boostcamp.and03.ui.navigation.Route
import com.boostcamp.and03.ui.screen.bookdetail.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(
    private val bookRepository: BookStorageRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val bookDetailRoute = savedStateHandle.toRoute<Route.BookDetail>()
    private val bookId: String = bookDetailRoute.bookId
    private val userId: String = "O12OmGoVY8FPYFElNjKN"
    private val _uiState = MutableStateFlow(BookDetailUiState())
    val uiState: StateFlow<BookDetailUiState> = _uiState.asStateFlow()

    init {
        loadAllData()
    }

    fun loadAllData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            try {
                loadCharacters()
                loadQuotes()
                loadMemos()
                loadBookInfo()
                _uiState.update { it.copy(isLoading = false) }
//                throw Exception("테스트용")
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, errorMessage = "데이터를 불러오는데 실패했습니다.")
                }
                Log.d("BookDetailViewModel", "loadAllData: ${e.message}")
            }
        }
    }

    private suspend fun loadCharacters() {
        val result = bookRepository.getCharacters(userId, bookId)
        _uiState.update {
            it.copy(
                characters = result.map { character -> character.toUiModel() }
                    .toPersistentList()
            )
        }
    }

    private suspend fun loadQuotes() {
        val result = bookRepository.getQuotes(userId, bookId)
        _uiState.update {
            it.copy(
                quotes = result.map { quote -> quote.toUiModel() }.toPersistentList()
            )
        }
    }

    private suspend fun loadMemos() {
        val result = bookRepository.getMemos(userId, bookId)
        _uiState.update {
            it.copy(
                memos = result.map { memo -> memo.toUiModel() }.toPersistentList()
            )
        }
    }

    private suspend fun loadBookInfo() {
        val result = bookRepository.getBookDetail(userId, bookId)
        if (result != null) {
            _uiState.update {
                it.copy(
                    thumbnail = result.thumbnail,
                    title = result.title,
                    author = result.author.joinToString(", "),
                    publisher = result.publisher
                )
            }
        }
    }

    //
//    fun addCharacter() {
//        viewModelScope.launch {
//            bookRepository.addCharacter(
//                userId, bookId, CharacterRequest(
//                    role = "주인공",
//                    description = "설명설명설명",
//                    name = "이름이름"
//                )
//            )
//        }
//    }

    //
//    fun addQuote(
//        userId: String = "O12OmGoVY8FPYFElNjKN",
//        bookId: String = "YkFyRg6G0v2Us6b3V5Tm"
//    ) {
//        viewModelScope.launch {
//            bookRepository.addQuote(
//                userId, bookId, QuoteRequest(
//                    content = "어쩌고저쩌고어쩌고저쩌고어쩌고저쩌고어쩌고저쩌고",
//                    createdAt = "2026.1.5",
//                    page = 121
//                )
//            )
//        }
//    }
//
    fun deleteCharacter(characterId: String) {
        _uiState.update {
            it.copy(
                characters = it.characters
                    .filterNot { character -> character.id == characterId }
                    .toPersistentList()
            )
        }

        viewModelScope.launch {
            try {
                bookRepository.deleteCharacter(
                    userId,
                    bookId,
                    characterId
                )
            } catch (e: Exception) {
                Log.d("BookDetailViewModel", "deleteCharacter: ${e.message}")
                loadCharacters()
            }
        }
    }
//
//    private suspend fun addTextMemo(
//    userId: String = "O12OmGoVY8FPYFElNjKN",
//    bookId: String = "YkFyRg6G0v2Us6b3V5Tm"
//) {
//    bookRepository.addTextMemo(
//        userId, bookId, TextMemoRequest(
//            title = "메모 제목",
//            content = "메모 내용",
//            startPage = 1,
//            endPage = 2
//        )
//    )
//}


}