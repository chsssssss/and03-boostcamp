package com.boostcamp.and03.ui.screen.bookdetail

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.boostcamp.and03.ui.screen.bookdetail.model.CharacterUiModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class BookDetailViewModel @Inject constructor() : ViewModel() {
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
            )
        )
    }
}