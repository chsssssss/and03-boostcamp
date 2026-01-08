package com.boostcamp.and03.ui.screen.booklist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boostcamp.and03.data.repository.book.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.persistentListOf
import com.boostcamp.and03.ui.screen.booklist.BooklistUiState
import com.boostcamp.and03.ui.screen.booklist.model.BookUiModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    private val searchQuery = MutableStateFlow("")

    private val userId = "O12OmGoVY8FPYFElNjKN" // 임시

    init {
        loadBooks()
        observeSearch()
    }

    fun loadBooks() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val books = bookRepository.loadSavedBooks(userId)

            _uiState.update {
                it.copy(
                    books = books.toImmutableList(),
                    isLoading = false
                )
            }
        }
    }

    @OptIn(FlowPreview::class)
    private fun observeSearch() {
        searchQuery
            .debounce(300)
            .distinctUntilChanged()
            .onEach { query ->
                val filtered = if (query.isBlank()) {
                    _uiState.value.books
                } else {
                    _uiState.value.books.filter {
                        it.title.contains(query, ignoreCase = true) ||
                                it.authors.any { author ->
                                    author.contains(query, ignoreCase = true)
                                }
                    }
                }

                _uiState.value = _uiState.value.copy(
                    searchQuery = query,
                    books = filtered.toImmutableList()
                )
            }
            .launchIn(viewModelScope)
    }

    fun onSearchQueryChange(query: String) {
        searchQuery.value = query
    }
}