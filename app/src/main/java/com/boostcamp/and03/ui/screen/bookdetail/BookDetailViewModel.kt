package com.boostcamp.and03.ui.screen.bookdetail

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.boostcamp.and03.ui.screen.bookdetail.model.CharacterUiModel
import com.boostcamp.and03.ui.screen.bookdetail.model.QuoteUiModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class BookDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState = MutableStateFlow(BookDetailUiState())
    val uiState: StateFlow<BookDetailUiState> = _uiState.asStateFlow()

    init {
        initPreviewData()
    }

    private fun initPreviewData() {
        _uiState.value = BookDetailUiState(
            thumbnail = "https://i.pinimg.com/736x/8f/20/41/8f2041520696507bc2bfd2f5648c8da3.jpg",
            title = "Harry Potter and the Philosopher's Stone",
            author = "J.K. Rowling",
            publisher = "Bloomsbury Publishing",
            characters = listOf(
                CharacterUiModel(
                    name = "해리 포터",
                    role = "주인공",
                    iconColor = Color(0xFF1E88E5),
                    description = "호그와트의 마법사 학생"
                ),
                CharacterUiModel(
                    name = "헤르미온느 그레인저",
                    role = "조연",
                    iconColor = Color(0xFF8E24AA),
                    description = "해리의 절친한 친구"
                )
            ),
            quotes = listOf(
                QuoteUiModel(
                    "1",
                    "이 책을 읽으면서 꿈에 대한 새로운 관점을 얻게 되었다. 꿈이 단순히 무의식의 산물이 아니라 우리가 구매할 수 있는 상품이라는 설정이 흥미롭다.",
                    26,
                    "2024.01.10"
                ),
                QuoteUiModel(
                    "2",
                    "이 책을 읽으면서 꿈에 대한 새로운 관점을 얻게 되었다. 꿈이 단순히 무의식의 산물이 아니라 우리가 구매할 수 있는 상품이라는 설정이 흥미롭다.",
                    26,
                    "2024.01.10"
                ),
                QuoteUiModel(
                    "3",
                    "이 책을 읽으면서 꿈에 대한 새로운 관점을 얻게 되었다. 꿈이 단순히 무의식의 산물이 아니라 우리가 구매할 수 있는 상품이라는 설정이 흥미롭다.",
                    26,
                    "2024.01.10"
                )
            )
        )
    }
}