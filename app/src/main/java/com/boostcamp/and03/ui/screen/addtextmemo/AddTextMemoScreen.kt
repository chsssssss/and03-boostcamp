package com.boostcamp.and03.ui.screen.addtextmemo

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun AddTextMemoRoute(
    navigateToBack: () -> Unit,
    viewModel: AddTextMemoViewModel = hiltViewModel()
) {
    AddTextMemoScreen(
        navigateToBack = navigateToBack
    )
}

@Composable
private fun AddTextMemoScreen(
    navigateToBack: () -> Unit
) {

}