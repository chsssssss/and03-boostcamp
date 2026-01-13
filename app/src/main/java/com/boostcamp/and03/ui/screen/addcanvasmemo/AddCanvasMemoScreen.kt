package com.boostcamp.and03.ui.screen.addcanvasmemo

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun AddCanvasMemoRoute(
    navigateToBack: () -> Unit,
    viewModel: AddCanvasMemoViewModel = hiltViewModel()
) {
    AddCanvasMemoScreen(
        navigateToBack = navigateToBack
    )
}

@Composable
private fun AddCanvasMemoScreen(
    navigateToBack: () -> Unit,
) {

}