package com.boostcamp.and03.ui.screen.booksearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.boostcamp.and03.data.repository.book.BookRepository
import com.boostcamp.and03.data.repository.book.toUiModel
import com.boostcamp.and03.ui.screen.booklist.model.BookUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class BookSearchViewModel @Inject constructor(
    private val bookRepository: BookRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(BookSearchUiState())
    val uiState = _uiState.asStateFlow()

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val pagingBooksFlow: Flow<PagingData<BookUIModel>> = _uiState
        .map { it.query }
        .debounce(300)
        .distinctUntilChanged()
        .filter { it.isNotBlank() }
        .flatMapLatest { query ->
            bookRepository.loadBooksPagingFlow(query)
                .map { pagingData ->
                    pagingData.map { it.toUiModel() }
                }
        }

    fun changeQuery(query: String) {
        _uiState.update { it.copy(query = query) }
    }

    fun clickItem(item: BookUIModel) {
        _uiState.update {
            it.copy(
                selectedBookISBN = if (it.selectedBookISBN == item.isbn) {
                    null
                } else {
                    item.isbn
                }
            )
        }
    }
}