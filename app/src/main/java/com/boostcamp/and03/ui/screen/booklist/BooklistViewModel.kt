package com.boostcamp.and03.ui.screen.booklist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.persistentListOf
import com.boostcamp.and03.ui.screen.booklist.model.BooklistUIState
import com.boostcamp.and03.ui.screen.booklist.model.BookUIModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@HiltViewModel
class BooklistViewModel @Inject constructor() : ViewModel() {

    private val allBooks = listOf(
        BookUIModel(
            title = "달러구트 꿈 백화점",
            authors = persistentListOf("이미예"),
            publisher = "팩토리나인",
            thumbnail = "",
            isbn = ""
        ),
        BookUIModel(
            title = "클린 아키텍처",
            authors = persistentListOf("로버트 C. 마틴"),
            publisher = "인사이트",
            thumbnail = "",
            isbn = ""
        ),
        BookUIModel(
            title = "객체지향의 사실과 오해",
            authors = persistentListOf("조영호"),
            publisher = "위키북스",
            thumbnail = "",
            isbn = ""
        )
    )

    private val searchQuery = MutableStateFlow("")

    private val _uiState = MutableStateFlow(
        BooklistUIState(books = allBooks)
    )
    val uiState: StateFlow<BooklistUIState> = _uiState.asStateFlow()

    init {
        observeSearch()
    }

    @OptIn(FlowPreview::class)
    private fun observeSearch() {
        searchQuery
            .debounce(300)
            .distinctUntilChanged()
            .onEach { query ->
                val filtered = if (query.isBlank()) {
                    allBooks
                } else {
                    allBooks.filter {
                        it.title.contains(query, ignoreCase = true) ||
                                it.authors.any { author ->
                                    author.contains(query, ignoreCase = true)
                                }
                    }
                }

                _uiState.value = _uiState.value.copy(
                    searchQuery = query,
                    books = filtered
                )
            }
            .launchIn(viewModelScope)
    }

    fun onSearchQueryChange(query: String) {
        searchQuery.value = query
    }
}
