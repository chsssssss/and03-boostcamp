package com.boostcamp.and03.ui.screen.characterform

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun CharacterFormRoute(
    navigateBack: () -> Unit,
    viewModel: CharacterFormViewModel = hiltViewModel()
) {

    CharacterFormScreen(
        onBackClick = navigateBack
    )
}

@Composable
private fun CharacterFormScreen(
    onBackClick: () -> Unit
) {

}