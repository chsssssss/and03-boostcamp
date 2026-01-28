package com.boostcamp.and03.ui.screen.bookdetail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.boostcamp.and03.data.repository.bookstorage.BookStorageRepository
import com.boostcamp.and03.ui.navigation.Route
import com.boostcamp.and03.ui.screen.bookdetail.model.BookDetailTab
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

            BookDetailAction.OnRetryBookInfo -> if (_uiState.value.bookInfoLoadState == LoadState.ERROR) {
                viewModelScope.launch { loadBookInfo() }
            }

            is BookDetailAction.OnRetryTab -> {
                when (action.tab) {
                    BookDetailTab.CHARACTER ->
                        if (_uiState.value.charactersLoadState == LoadState.ERROR) {
                            _uiState.update { it.copy(charactersLoadState = LoadState.LOADING) }
                            viewModelScope.launch { loadCharacters() }
                        }

                    BookDetailTab.QUOTE ->
                        if (_uiState.value.quotesLoadState == LoadState.ERROR) {
                            _uiState.update { it.copy(quotesLoadState = LoadState.LOADING) }
                            viewModelScope.launch { loadQuotes() }
                        }

                    BookDetailTab.MEMO ->
                        if (_uiState.value.memosLoadState == LoadState.ERROR) {
                            _uiState.update { it.copy(memosLoadState = LoadState.LOADING) }
                            viewModelScope.launch { loadMemos() }
                        }
                }
            }

            is BookDetailAction.OnTabSelect -> {
                when (action.tab) {
                    BookDetailTab.CHARACTER -> {
                        if (_uiState.value.charactersLoadState == LoadState.INIT) {
                            _uiState.update { it.copy(charactersLoadState = LoadState.LOADING) }
                            viewModelScope.launch { loadCharacters() }
                        }
                    }

                    BookDetailTab.QUOTE -> {
                        if (_uiState.value.quotesLoadState == LoadState.INIT) {
                            _uiState.update { it.copy(quotesLoadState = LoadState.LOADING) }
                            viewModelScope.launch { loadQuotes() }
                        }
                    }

                    BookDetailTab.MEMO -> {
                        if (_uiState.value.memosLoadState == LoadState.INIT) {
                            _uiState.update { it.copy(memosLoadState = LoadState.LOADING) }
                            viewModelScope.launch { loadMemos() }
                        }
                    }
                }
            }

            is BookDetailAction.OnOpenCharacterForm -> _event.trySend(
                BookDetailEvent.NavigateToCharacterForm(
                    action.bookId,
                    action.characterId
                )
            )

            is BookDetailAction.OnOpenQuoteForm -> _event.trySend(
                BookDetailEvent.NavigateToQuoteForm(
                    action.bookId,
                    action.quoteId,
                    uiState.value.totalPage
                )
            )

            is BookDetailAction.DeleteCharacter -> deleteCharacter(action.characterId)

            is BookDetailAction.DeleteQuote -> deleteQuote(action.quoteId)

            is BookDetailAction.DeleteMemo -> deleteMemo(action.memoId)

            is BookDetailAction.OnOpenTextMemoForm -> _event.trySend(
                BookDetailEvent.NavigateToTextMemoForm(
                    action.bookId,
                    action.memoId,
                    uiState.value.totalPage
                )
            )

            is BookDetailAction.OnOpenCanvasMemoForm -> _event.trySend(
                BookDetailEvent.NavigateToCanvasMemoForm(
                    action.bookId,
                    action.memoId,
                    uiState.value.totalPage
                )
            )

            is BookDetailAction.OnCanvasMemoClick -> _event.trySend(
                BookDetailEvent.NavigateToCanvas(
                    action.memoId
                )
            )
        }
    }

    init {
        _uiState.update {
            it.copy(
                bookInfoLoadState = LoadState.LOADING,
                charactersLoadState = LoadState.LOADING
            )
        }

        viewModelScope.launch {
            loadBookInfo()
            loadCharacters() // 화면 진입 시 등장인물 탭이 등장
        }
    }

    private suspend fun loadBookInfo() {
        try {
            bookRepository.getBookDetail(userId, bookId)?.let { result ->
                _uiState.update {
                    it.copy(
                        thumbnail = result.thumbnail,
                        title = result.title,
                        author = result.author.joinToString(", "),
                        publisher = result.publisher,
                        totalPage = result.totalPage,
                        bookInfoLoadState = LoadState.DONE
                    )
                }
            }
        } catch (e: Exception) {
            _uiState.update { it.copy(bookInfoLoadState = LoadState.ERROR) }
            Log.d("BookDetailViewModel", "loadBookInfo: ${e.message}")
        }
    }

    private fun loadCharacters() {
        viewModelScope.launch {
            try {
                bookRepository.getCharacters(userId, bookId).collect { result ->
                    Log.d("BookDetailViewModel", "observeCharacters: $result")
                    _uiState.update {
                        it.copy(
                            characters = result
                                .map { character -> character.toUiModel() }
                                .toPersistentList(),
                            charactersLoadState = LoadState.DONE
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(charactersLoadState = LoadState.ERROR) }
                Log.e("BookDetailViewModel", "observeCharacters error: ${e.message}")
            }
        }
    }

    private fun loadQuotes() {
        viewModelScope.launch {
            try {
                bookRepository.getQuotes(userId, bookId).collect { result ->
                    Log.d("BookDetailViewModel", "observeQuotes: $result")
                    _uiState.update {
                        it.copy(
                            quotes = result
                                .map { quote -> quote.toUiModel() }
                                .toPersistentList(),
                            quotesLoadState = LoadState.DONE
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(quotesLoadState = LoadState.ERROR) }
                Log.e("BookDetailViewModel", "observeQuotes error: ${e.message}")
            }
        }
    }

    private fun loadMemos() {
        viewModelScope.launch {
            try {
                bookRepository.getMemos(userId, bookId).collect { result ->
                    Log.d("BookDetailViewModel", "observeMemos: $result")
                    _uiState.update {
                        it.copy(
                            memos = result
                                .map { memo -> memo.toUiModel() }
                                .toPersistentList(),
                            memosLoadState = LoadState.DONE
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(memosLoadState = LoadState.ERROR) }
                Log.e("BookDetailViewModel", "observeMemos error: ${e.message}")
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