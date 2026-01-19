package com.boostcamp.and03.ui.screen.canvasmemoeditor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.boostcamp.and03.R
import com.boostcamp.and03.ui.component.And03AppBar
import com.boostcamp.and03.ui.component.And03Button
import com.boostcamp.and03.ui.component.ButtonVariant
import com.boostcamp.and03.ui.component.PageInputSection
import com.boostcamp.and03.ui.component.TitleInputSection
import com.boostcamp.and03.ui.screen.canvasmemoeditor.model.CanvasMemoEditorAction
import com.boostcamp.and03.ui.screen.canvasmemoeditor.model.CanvasMemoEditorEvent
import com.boostcamp.and03.ui.theme.And03ComponentSize
import com.boostcamp.and03.ui.theme.And03Padding
import com.boostcamp.and03.ui.theme.And03Spacing
import com.boostcamp.and03.ui.theme.And03Theme
import com.boostcamp.and03.ui.util.collectWithLifecycle

@Composable
fun CanvasMemoEditorRoute(
    navigateBack: () -> Unit,
    viewModel: CanvasMemoEditorViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    viewModel.event.collectWithLifecycle { event ->
        when (event) {
            CanvasMemoEditorEvent.NavigateBack -> navigateBack()
        }
    }

    CanvasMemoEditorScreen(
        uiState = uiState,
        onAction = viewModel::onAction
    )
}

@Composable
private fun CanvasMemoEditorScreen(
    uiState: CanvasMemoEditorUiState,
    onAction: (CanvasMemoEditorAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            And03AppBar(
                title = stringResource(id = R.string.add_memo_canvas_app_bar_title),
                onBackClick = { onAction(CanvasMemoEditorAction.OnBackClick) }
            )
        },
        bottomBar = {
            And03Button(
                text = stringResource(id = R.string.add_memo_save_button_text),
                onClick = { onAction(CanvasMemoEditorAction.OnSaveClick) },
                variant = ButtonVariant.Primary,
                enabled = uiState.isSaveable,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(And03Padding.PADDING_L)
                    .height(And03ComponentSize.BUTTON_HEIGHT_L)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(And03Padding.PADDING_L),
            verticalArrangement = Arrangement.spacedBy(And03Spacing.SPACE_L)
        ) {
            TitleInputSection(
                title = uiState.title,
                onTitleChange = { onAction(CanvasMemoEditorAction.OnTitleChange(title = it)) }
            )

            PageInputSection(
                startPage = uiState.startPage,
                endPage = uiState.endPage,
                onStartPageChange = { onAction(CanvasMemoEditorAction.OnStartPageChange(startPage = it)) },
                onEndPageChange = { onAction(CanvasMemoEditorAction.OnEndPageChange(endPage = it)) }
            )
        }
    }
}

@Preview
@Composable
private fun CanvasMemoEditorScreenPreview() {
    And03Theme {
        CanvasMemoEditorScreen(
            uiState = CanvasMemoEditorUiState(),
            onAction = {}
        )
    }
}