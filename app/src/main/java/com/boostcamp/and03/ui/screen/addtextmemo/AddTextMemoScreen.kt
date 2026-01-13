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
import com.boostcamp.and03.ui.theme.And03ComponentSize
import com.boostcamp.and03.ui.theme.And03Padding
import com.boostcamp.and03.ui.theme.And03Spacing
import com.boostcamp.and03.ui.theme.And03Theme

@Composable
fun AddTextMemoRoute(
    navigateToBack: () -> Unit,
    viewModel: AddTextMemoViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    AddTextMemoScreen(
        uiState = uiState,
        onBackClick = navigateToBack,
        onSaveClick = viewModel::saveTextMemo,
        onTitleChange = { viewModel.updateTitle(uiState.title) },
        onContentChange = { viewModel.updateContent(uiState.content) },
        onStartPageChange = { viewModel.updateStartPage(uiState.startPage) },
        onEndPageChange = { viewModel.updateEndPage(uiState.endPage) }
    )
}

@Composable
private fun AddTextMemoScreen(
    uiState: AddTextMemoUiState,
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit,
    onTitleChange: (String) -> Unit,
    onContentChange: (String) -> Unit,
    onStartPageChange: (String) -> Unit,
    onEndPageChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var isOCRBottomSheetVisible by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            And03AppBar(
                title = stringResource(id = R.string.add_memo_text_app_bar_title),
                onBackClick = onBackClick
            )
        },
        bottomBar = {
            And03Button(
                text = stringResource(id = R.string.add_memo_save_button_text),
                onClick = onSaveClick,
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
                onTitleChange = onTitleChange
            )

            ContentInputSection(
                label = stringResource(R.string.add_memo_content),
                content = uiState.content,
                onContentChange = onContentChange,
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
                onStartPageChange = onStartPageChange,
                onEndPageChange = onEndPageChange
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
            onBackClick = {},
            onSaveClick = {},
            onTitleChange = {},
            onContentChange = {},
            onStartPageChange = {},
            onEndPageChange = {}
        )
    }
}