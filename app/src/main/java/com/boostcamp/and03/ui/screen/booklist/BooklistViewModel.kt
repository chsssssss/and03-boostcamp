package com.boostcamp.and03.ui.screen.booklist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boostcamp.and03.data.repository.book_storage.BookStorageRepository
import com.boostcamp.and03.ui.screen.booklist.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BooklistViewModel @Inject constructor(
    private val bookStorageRepository: BookStorageRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(BooklistUiState())
    val uiState = _uiState.asStateFlow()

    private val userId = "O12OmGoVY8FPYFElNjKN" // 임시

    init {
        loadBooks()
        resetSearch()
    }

    fun loadBooks() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val books = bookStorageRepository.getBooks(userId)
                .map { it.toUiModel() }
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
        _uiState.update {
            it.copy(
                searchQuery = "",
                filteredBooks = it.allBooks
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