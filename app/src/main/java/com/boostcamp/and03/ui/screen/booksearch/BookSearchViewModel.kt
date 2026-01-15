package com.boostcamp.and03.ui.screen.booksearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.boostcamp.and03.data.model.request.toRequest
import com.boostcamp.and03.data.repository.booksearch.BookSearchRepository
import com.boostcamp.and03.data.repository.book_storage.BookStorageRepository
import com.boostcamp.and03.ui.screen.booksearch.model.BookSearchAction
import com.boostcamp.and03.ui.screen.booksearch.model.BookSearchEvent
import com.boostcamp.and03.ui.screen.booksearch.model.BookSearchResultUiModel
import com.boostcamp.and03.ui.screen.booksearch.model.SaveFailureReason
import com.boostcamp.and03.ui.screen.booksearch.model.toUiModel
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
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookSearchViewModel @Inject constructor(
    private val bookSearchRepository: BookSearchRepository,
    private val bookStorageRepository: BookStorageRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(BookSearchUiState())
    val uiState = _uiState.asStateFlow()

    private val _event: Channel<BookSearchEvent> = Channel(BUFFERED)
    val event = _event.receiveAsFlow()

    fun onAction(action: BookSearchAction) {
        when (action) {
            BookSearchAction.OnBackClick -> _event.trySend(BookSearchEvent.NavigateBack)

            BookSearchAction.OnManualAddClick -> _event.trySend(BookSearchEvent.NavigateToManualAdd)

            is BookSearchAction.OnQueryChange -> _uiState.update { it.copy(query = action.query) }

            is BookSearchAction.OnItemClick -> _uiState.update {
                val isSelected = it.selectedBook == action.item

                it.copy(selectedBook = if (isSelected) null else action.item)
            }

            BookSearchAction.OnSaveClick -> saveItem()
        }
    }

    @OptIn(FlowPreview::class)
    private val queryFlow: Flow<String> = _uiState
        .map { it.query }
        .debounce(300)
        .map { it.trim() }
        .distinctUntilChanged()

    @OptIn(ExperimentalCoroutinesApi::class)
    val pagingBooksFlow: Flow<PagingData<BookSearchResultUiModel>> =
        queryFlow
            .filter { it.isNotBlank() }
            .flatMapLatest { query ->
                bookSearchRepository.loadSearchResults(query)
                    .map { pagingData -> pagingData.map { it.toUiModel() } }
            }
            .cachedIn(viewModelScope)

    @OptIn(ExperimentalCoroutinesApi::class)
    val totalResultCountFlow: Flow<Int> =
        queryFlow
            .filter { it.isNotBlank() }
            .flatMapLatest { query ->
                flow { emit(bookSearchRepository.loadTotalSearchResultCount(query)) }
            }

    // 임시 userId 사용
    private fun saveItem(userId: String = "O12OmGoVY8FPYFElNjKN") {
        val selectedSearchResult = _uiState.value.selectedBook ?: return

        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true) }

            try {
                val totalPage = bookSearchRepository.loadBookPage(itemId = selectedSearchResult.isbn)

                val book = BookSearchResultUiModel(
                    title = selectedSearchResult.title,
                    authors = selectedSearchResult.authors,
                    publisher = selectedSearchResult.publisher,
                    thumbnail = selectedSearchResult.thumbnail,
                    isbn = selectedSearchResult.isbn,
                    totalPage = totalPage
                ).toRequest()

                bookStorageRepository.saveBook(userId, book)

                _event.trySend(BookSearchEvent.NavigateBack)
            } catch (e: Exception) {
                _event.trySend(BookSearchEvent.SaveFailure(SaveFailureReason.Unknown))
            } finally {
                _uiState.update {
                    it.copy(
                        isSaving = false,
                        selectedBook = null
                    )
                }
            }
        }
    }
}