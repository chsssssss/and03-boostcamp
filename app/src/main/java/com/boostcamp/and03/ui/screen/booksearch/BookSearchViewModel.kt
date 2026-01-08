package com.boostcamp.and03.ui.screen.booksearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.boostcamp.and03.data.repository.book.BookRepository
import com.boostcamp.and03.data.repository.book.toUiModel
import com.boostcamp.and03.ui.screen.booksearch.model.BookSearchResultUiModel
import com.boostcamp.and03.ui.screen.booksearch.model.BookSearchEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookSearchViewModel @Inject constructor(
    private val bookRepository: BookRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(BookSearchUiState())
    val uiState = _uiState.asStateFlow()

    private val _event: Channel<BookSearchEvent> = Channel(BUFFERED)
    val event = _event.receiveAsFlow()

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val pagingBooksFlow: Flow<PagingData<BookSearchResultUiModel>> = _uiState
        .map { it.query }
        .debounce(300)
        .distinctUntilChanged()
        .filter { it.isNotBlank() }
        .onEach { query ->
            val total = bookRepository.loadTotalResultCount(query)
            _uiState.update  { it.copy(totalResultCount = total) }
        }
        .flatMapLatest { query ->
            bookRepository.loadBooksPagingFlow(query)
                .map { pagingData ->
                    pagingData.map { it.toUiModel() }
                }
        }
        .cachedIn(viewModelScope)

    fun changeQuery(query: String) {
        _uiState.update { it.copy(query = query) }
    }

    fun clickItem(item: BookSearchResultUiModel) {
        _uiState.update { state ->
            val isSelected = state.selectedBook?.isbn == item.isbn

            state.copy(selectedBook = if (isSelected) null else item)
        }
    }

    // 임시 userId 사용
    fun saveItem(userId: String = "O12OmGoVY8FPYFElNjKN") {
        val book = _uiState.value.selectedBook ?: return

        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true) }

            try {
                // TODO: Repository 구현 후 연결
                // bookRepository.saveBook(userId, book)

            } finally {
                _uiState.update { it.copy(isSaving = false) }
            }
        }
    }
}