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
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
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

    private val _uiState = MutableStateFlow(BookDetailUiState(bookId = bookId))
    val uiState: StateFlow<BookDetailUiState> = _uiState.asStateFlow()

    private val _event: Channel<BookDetailEvent> = Channel(BUFFERED)
    val event = _event.receiveAsFlow()

    fun onAction(action: BookDetailAction) {
        when (action) {
            BookDetailAction.OnBackClick -> _event.trySend(BookDetailEvent.NavigateBack)

            BookDetailAction.OnRetryClick -> loadAllData()

            is BookDetailAction.OnTabSelect -> _uiState.update { it.copy(selectedTabIndex = action.index) }

            is BookDetailAction.OnOpenCharacterForm -> _event.trySend(
                BookDetailEvent.NavigateToCharacterForm(
                    action.bookId,
                    action.characterId
                )
            )

            is BookDetailAction.OnOpenQuoteForm -> _event.trySend(
                BookDetailEvent.NavigateToQuoteForm(
                    action.bookId,
                    action.quoteId
                )
            )

            is BookDetailAction.DeleteCharacter -> deleteCharacter(action.characterId)

            is BookDetailAction.DeleteQuote -> deleteQuote(action.quoteId)

            is BookDetailAction.DeleteMemo -> deleteMemo(action.memoId)

            is BookDetailAction.OnOpenTextMemoForm -> _event.trySend(
                BookDetailEvent.NavigateToTextMemoForm(
                    action.bookId,
                    action.memoId
                )
            )

            is BookDetailAction.OnOpenCanvasMemoForm -> _event.trySend(
                BookDetailEvent.NavigateToCanvasMemoForm(
                    action.bookId,
                    action.memoId
                )
            )

            is BookDetailAction.OnCanvasMemoClick -> _event.trySend(BookDetailEvent.NavigateToCanvas(action.memoId))
        }
    }

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
                    publisher = result.publisher,
                    totalPage = result.totalPage
                )
            }
        }
    }

    fun deleteCharacter(characterId: String) {
        val previousCharacters = _uiState.value.characters

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

                _uiState.update { it.copy(characters = previousCharacters) }

                // TODO : 사용자에게 Snackbar로 알림을 줘야함
            }
        }
    }

    fun deleteQuote(quoteId: String) {
        val previousQuotes = _uiState.value.quotes

        _uiState.update {
            it.copy(
                quotes = it.quotes
                    .filterNot { quote -> quote.id == quoteId }
                    .toPersistentList()
            )
        }

        viewModelScope.launch {
            try {
                bookRepository.deleteQuote(
                    userId,
                    bookId,
                    quoteId
                )
            } catch (e: Exception) {
                Log.d("BookDetailViewModel", "deleteQuote: ${e.message}")

                _uiState.update { it.copy(quotes = previousQuotes) }

                // TODO : 사용자에게 Snackbar로 알림을 줘야함
            }
        }
    }

    fun deleteMemo(memoId: String) {
        val previousMemos = _uiState.value.memos

        _uiState.update {
            it.copy(
                memos = it.memos
                    .filterNot { memo -> memo.id == memoId }
                    .toPersistentList()
            )
        }

        viewModelScope.launch {
            try {
                bookRepository.deleteMemo(
                    userId,
                    bookId,
                    memoId
                )
            } catch (e: Exception) {
                Log.d("BookDetailViewModel", "deleteMemo: ${e.message}")

                _uiState.update { it.copy(memos = previousMemos) }

                // TODO : 사용자에게 Snackbar로 알림을 줘야함
            }
        }
    }
}