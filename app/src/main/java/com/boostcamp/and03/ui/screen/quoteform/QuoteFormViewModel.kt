package com.boostcamp.and03.ui.screen.quoteform

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.boostcamp.and03.data.repository.book_storage.BookStorageRepository
import com.boostcamp.and03.ui.navigation.Route
import com.boostcamp.and03.ui.screen.bookdetail.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuoteFormViewModel @Inject constructor(
    private val bookStorageRepository: BookStorageRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val quoteFormRoute = savedStateHandle.toRoute<Route.QuoteForm>()
    private val bookId = quoteFormRoute.bookId
    private val quoteId = quoteFormRoute.quoteId

    private val userId: String = "O12OmGoVY8FPYFElNjKN"

    private val _uiState = MutableStateFlow(QuoteFormUiState())
    val uiState = _uiState.asStateFlow()

    private val _event: Channel<QuoteFormEvent> = Channel(BUFFERED)
    val event = _event.receiveAsFlow()

    fun onAction(action: QuoteFormAction) {
        when (action) {
            QuoteFormAction.OnBackClick -> _event.trySend(QuoteFormEvent.NavigateBack)

            QuoteFormAction.OnSaveClick -> {
                viewModelScope.launch {
                    try {
                        saveQuote()
                        _event.trySend(QuoteFormEvent.NavigateBack)
                    } catch (e: Exception) {
                        // TODO: 오류 메시지 UI 구현
                    }
                }
            }

            is QuoteFormAction.OnQuoteChange -> _uiState.update { it.copy(quote = action.quote) }

            is QuoteFormAction.OnPageChange -> {
                _uiState.update {
                    it.copy(
                        page = action.page.filter {
                            char -> char.isDigit()
                        }
                    )
                }
            }
        }
    }

    init {
        viewModelScope.launch {
            loadQuote()
        }
    }

    private suspend fun loadQuote() {
        if (quoteId.isBlank()) return

        val result = bookStorageRepository.getQuote(
            userId = userId,
            bookId = bookId,
            quoteId = quoteId
        )

        _uiState.update {
            it.copy(
                quote = result.content,
                page = result.page.toString()
            )
        }
    }

    private suspend fun saveQuote() {
        if (quoteId.isBlank()) {
            bookStorageRepository.addQuote(
                userId = userId,
                bookId = bookId,
                quote = _uiState.value.toUiModel()
            )
        } else {
            bookStorageRepository.updateQuote(
                userId = userId,
                bookId = bookId,
                quoteId = quoteId,
                quote = _uiState.value.toUiModel()
            )
        }
    }
}