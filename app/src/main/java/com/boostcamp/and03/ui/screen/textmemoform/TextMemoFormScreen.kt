package com.boostcamp.and03.ui.screen.textmemoform

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.boostcamp.and03.R
import com.boostcamp.and03.ui.component.And03AppBar
import com.boostcamp.and03.ui.component.And03Button
import com.boostcamp.and03.ui.component.And03Dialog
import com.boostcamp.and03.ui.component.ButtonVariant
import com.boostcamp.and03.ui.component.ContentInputSection
import com.boostcamp.and03.ui.component.DialogDismissAction
import com.boostcamp.and03.ui.component.OCRBottomSheet
import com.boostcamp.and03.ui.component.PageInputSection
import com.boostcamp.and03.ui.component.TitleInputSection
import com.boostcamp.and03.ui.theme.And03ComponentSize
import com.boostcamp.and03.ui.theme.And03Padding
import com.boostcamp.and03.ui.theme.And03Spacing
import com.boostcamp.and03.ui.theme.And03Theme
import com.boostcamp.and03.ui.util.collectWithLifecycle

@Composable
fun TextMemoFormRoute(
    navigateBack: () -> Unit,
    viewModel: TextMemoFormViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    viewModel.event.collectWithLifecycle { event ->
        when (event) {
            TextMemoFormEvent.NavigateBack -> navigateBack()
        }
    }

    TextMemoFormScreen(
        uiState = uiState,
        onAction = viewModel::onAction
    )
}

@Composable
private fun TextMemoFormScreen(
    uiState: TextMemoFormUiState,
    onAction: (TextMemoFormAction) -> Unit,
    modifier: Modifier = Modifier
) {
    var isOCRBottomSheetVisible by remember { mutableStateOf(false) }

    BackHandler {
        if (uiState.isExitConfirmationDialogVisible) {
            onAction(TextMemoFormAction.CloseExitConfirmationDialog)
        } else if (uiState.isEdited) {
            onAction(TextMemoFormAction.OnBackClick)
        } else {
            onAction(TextMemoFormAction.CloseScreen)
        }
    }

    Scaffold(
        topBar = {
            And03AppBar(
                title = stringResource(id = R.string.add_memo_text_app_bar_title),
                onBackClick = { onAction(TextMemoFormAction.OnBackClick) }
            )
        },
        bottomBar = {
            And03Button(
                text = if(!uiState.isSaving) {
                    stringResource(id = R.string.add_memo_save_button_text)
                } else {
                    stringResource(id = R.string.add_memo_saving_button_text)
                },
                onClick = { onAction(TextMemoFormAction.OnSaveClick) },
                variant = ButtonVariant.Primary,
                enabled = uiState.isSaveable && !uiState.isSaving,
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(
                        WindowInsets.safeDrawing.only(
                            WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom
                        )
                    )
                    .padding(And03Padding.PADDING_L)
                    .height(And03ComponentSize.BUTTON_HEIGHT_L)
            )
        }
    ) { innerPadding ->
        if (uiState.isLoading) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    modifier = Modifier.align(alignment = Alignment.Center)
                )
            }
        } else {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(And03Padding.PADDING_L)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(And03Spacing.SPACE_L)
            ) {
                TitleInputSection(
                    title = uiState.title,
                    onTitleChange = { onAction(TextMemoFormAction.OnTitleChange(title = it)) }
                )

                ContentInputSection(
                    label = stringResource(R.string.add_memo_content),
                    content = uiState.content,
                    onContentChange = { onAction(TextMemoFormAction.OnContentChange(content = it)) },
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
                    onStartPageChange = { onAction(TextMemoFormAction.OnStartPageChange(startPage = it)) },
                    onEndPageChange = { onAction(TextMemoFormAction.OnEndPageChange(endPage = it)) },
                    totalPage = uiState.totalPage
                )
            }
        }

        if (uiState.isEdited && uiState.isExitConfirmationDialogVisible) {
            And03Dialog(
                iconResId = R.drawable.ic_warning_filled,
                iconColor = And03Theme.colors.error,
                iconContentDescription = stringResource(id = R.string.content_description_caution),
                title = stringResource(id = R.string.canvas_memo_exit_confirmation_dialog_title),
                dismissText = stringResource(id = R.string.canvas_memo_exit_confirmation_dialog_dismiss_text),
                confirmText = stringResource(id = R.string.canvas_memo_exit_confirmation_dialog_confirm_text),
                onDismiss = { onAction(TextMemoFormAction.CloseScreen) },
                onConfirm = { onAction(TextMemoFormAction.CloseExitConfirmationDialog) },
                description = stringResource(id = R.string.canvas_memo_exit_confirmation_dialog_description),
                dismissAction = DialogDismissAction.Confirm
            )
        }
    }
}

@Preview
@Composable
private fun TextMemoEditorScreenPreview() {
    And03Theme {
        TextMemoFormScreen(
            uiState = TextMemoFormUiState(),
            onAction = {}
        )
    }
}