package com.boostcamp.and03.ui.screen.booklist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boostcamp.and03.data.repository.book.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class BooklistViewModel @Inject constructor(
    private val bookRepository: BookRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(BooklistUiState())
    val uiState = _uiState.asStateFlow()

    private val userId = "O12OmGoVY8FPYFElNjKN" // 임시

    init {
        loadBooks()
    }

    fun loadBooks() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val books = bookRepository.loadSavedBooks(userId)
                .toImmutableList()

            _uiState.update {
                it.copy(
                    allBooks = books,
                    filteredBooks = books,
                    isLoading = false
                )
            }
        }
    }

    fun resetSearch() {
        _uiState.update { state ->
            state.copy(
                searchQuery = "",
                filteredBooks = state.allBooks
            )
        }
    }

    fun onSearchQueryChange(query: String) {
        _uiState.update { state ->
            val filtered = if (query.isBlank()) {
                state.allBooks
            } else {
                state.allBooks.filter { book ->
                    book.title.contains(query, ignoreCase = true) ||
                            book.authors.any { author ->
                                author.contains(query, ignoreCase = true)
                            }
                }.toImmutableList()
            }

            state.copy(
                searchQuery = query,
                filteredBooks = filtered
            )
        }
    }
}