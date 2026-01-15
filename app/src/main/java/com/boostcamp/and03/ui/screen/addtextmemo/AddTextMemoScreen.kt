package com.boostcamp.and03.ui.screen.addtextmemo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.boostcamp.and03.R
import com.boostcamp.and03.ui.component.And03AppBar
import com.boostcamp.and03.ui.component.And03Button
import com.boostcamp.and03.ui.component.ButtonVariant
import com.boostcamp.and03.ui.component.ContentInputSection
import com.boostcamp.and03.ui.component.OCRBottomSheet
import com.boostcamp.and03.ui.component.PageInputSection
import com.boostcamp.and03.ui.component.TitleInputSection
import com.boostcamp.and03.ui.screen.addtextmemo.model.AddTextMemoAction
import com.boostcamp.and03.ui.screen.addtextmemo.model.AddTextMemoEvent
import com.boostcamp.and03.ui.theme.And03ComponentSize
import com.boostcamp.and03.ui.theme.And03Padding
import com.boostcamp.and03.ui.theme.And03Spacing
import com.boostcamp.and03.ui.theme.And03Theme
import com.boostcamp.and03.ui.util.collectWithLifecycle

@Composable
fun AddTextMemoRoute(
    navigateBack: () -> Unit,
    viewModel: AddTextMemoViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    viewModel.event.collectWithLifecycle { event ->
        when (event) {
            AddTextMemoEvent.NavigateBack -> navigateBack()
        }
    }

    AddTextMemoScreen(
        uiState = uiState,
        onAction = viewModel::onAction
    )
}

@Composable
private fun AddTextMemoScreen(
    uiState: AddTextMemoUiState,
    onAction: (AddTextMemoAction) -> Unit,
    modifier: Modifier = Modifier
) {
    var isOCRBottomSheetVisible by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            And03AppBar(
                title = stringResource(id = R.string.add_memo_text_app_bar_title),
                onBackClick = { onAction(AddTextMemoAction.OnBackClick) }
            )
        },
        bottomBar = {
            And03Button(
                text = stringResource(id = R.string.add_memo_save_button_text),
                onClick = { onAction(AddTextMemoAction.OnSaveClick) },
                variant = ButtonVariant.Primary,
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
                onTitleChange = { onAction(AddTextMemoAction.OnTitleChange(title = it)) }
            )

            ContentInputSection(
                label = stringResource(R.string.add_memo_content),
                content = uiState.content,
                onContentChange = { onAction(AddTextMemoAction.OnContentChange(content = it)) },
                onAddByImageClick = { isOCRBottomSheetVisible = true },
            )

            if (isOCRBottomSheetVisible) {
                OCRBottomSheet(
                    onDismiss = { isOCRBottomSheetVisible = false },
                    onCameraClick = { /* TODO: 텍스트 가져오기 기능 구현 */ },
                    onGalleryClick = { /* TODO: 사진 촬영 기능 구현 */ }
                )
            }

            PageInputSection(
                startPage = uiState.startPage,
                endPage = uiState.endPage,
                onStartPageChange = { onAction(AddTextMemoAction.OnStartPageChange(startPage = it)) },
                onEndPageChange = { onAction(AddTextMemoAction.OnEndPageChange(endPage = it)) }
            )
        }
    }
}

@Preview
@Composable
private fun AddTextMemoScreenPreview() {
    And03Theme {
        AddTextMemoScreen(
            uiState = AddTextMemoUiState(),
            onAction = {}
        )
    }
}