package com.boostcamp.and03.ui.screen.canvasmemo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.boostcamp.and03.R
import com.boostcamp.and03.ui.screen.canvasmemo.component.ToolAction
import com.boostcamp.and03.ui.screen.canvasmemo.component.ToolExpandableButton
import com.boostcamp.and03.ui.theme.And03Padding

@Composable
fun CanvasMemoRoute(
    navigateToBack: () -> Unit,
    viewModel: CanvasMemoViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                is CanvasMemoEvent.NavToBack -> navigateToBack()
            }
        }
    }

    CanvasMemoScreen(
        uiState = uiState,
        onAction = viewModel::onAction,
    )
}

@Composable
private fun CanvasMemoScreen(
    uiState: CanvasMemoUiState,
    onAction: (CanvasMemoAction) -> Unit,
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        ToolExpandableButton(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = And03Padding.PADDING_L, bottom = And03Padding.PADDING_2XL),
            actions = listOf(
                ToolAction(
                    iconRes = R.drawable.ic_add_filled,
                    contentDescription = stringResource(R.string.tool_ic_content_desc_zoom_in),
                    onClick = { }
                ),
                ToolAction(
                    iconRes = R.drawable.ic_remove_filled,
                    contentDescription = stringResource(R.string.tool_ic_content_desc_zoom_out),
                    onClick = { }
                ),
                ToolAction(
                    iconRes = R.drawable.ic_fit_screen,
                    contentDescription = stringResource(R.string.tool_ic_content_desc_fit_screen),
                    onClick = { }
                )
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CanvasMemoScreenPreview() {
    val previewState = CanvasMemoUiState()

    CanvasMemoScreen(
        uiState = previewState,
        onAction = {},
    )

}