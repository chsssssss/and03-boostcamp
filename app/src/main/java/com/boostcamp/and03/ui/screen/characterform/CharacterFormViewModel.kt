package com.boostcamp.and03.ui.screen.characterform

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.boostcamp.and03.data.repository.book_storage.BookStorageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharacterFormViewModel @Inject constructor (
    private val bookStorageRepository: BookStorageRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

}