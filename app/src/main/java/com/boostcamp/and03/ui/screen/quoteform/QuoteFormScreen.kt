package com.boostcamp.and03.ui.screen.quoteform

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.boostcamp.and03.R
import com.boostcamp.and03.ui.component.AddTextByImageButton
import com.boostcamp.and03.ui.component.And03AppBar
import com.boostcamp.and03.ui.component.And03Button
import com.boostcamp.and03.ui.component.And03InfoSection
import com.boostcamp.and03.ui.component.ButtonVariant
import com.boostcamp.and03.ui.component.OCRBottomSheet
import com.boostcamp.and03.ui.component.PageInputSection
import com.boostcamp.and03.ui.theme.And03ComponentSize
import com.boostcamp.and03.ui.theme.And03Padding
import com.boostcamp.and03.ui.theme.And03Spacing
import com.boostcamp.and03.ui.theme.And03Theme
import com.boostcamp.and03.ui.util.collectWithLifecycle

private object QuoteFormScreenValues {
    const val MAX_CHARACTER_COUNT = 500
}

@Composable
fun QuoteFormRoute(
    navigateBack: () -> Unit,
    viewModel: QuoteFormViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    viewModel.event.collectWithLifecycle { event ->
        when (event) {
            QuoteFormEvent.NavigateBack -> navigateBack()
        }
    }

    QuoteFormScreen(
        uiState = uiState,
        onAction = viewModel::onAction
    )
}

@Composable
private fun QuoteFormScreen(
    uiState: QuoteFormUiState,
    onAction: (QuoteFormAction) -> Unit,
) {
    var isOCRBottomSheetVisible by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            And03AppBar(
                title = stringResource(R.string.add_quote_title),
                onBackClick = { onAction(QuoteFormAction.OnBackClick) }
            )
        },
        bottomBar = {
            And03Button(
                text = if(!uiState.isSaving) {
                    stringResource(id = R.string.add_memo_save_button_text)
                } else {
                    stringResource(id = R.string.add_memo_saving_button_text)
                },
                onClick = { onAction(QuoteFormAction.OnSaveClick) },
                enabled = uiState.isSaveable && !uiState.isSaving,
                variant = ButtonVariant.Primary,
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
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(And03Padding.PADDING_L)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(And03Spacing.SPACE_L),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            And03InfoSection(
                title = stringResource(R.string.add_quote_info_title),
                description = stringResource(R.string.add_quote_info_description)
            )

            QuoteInputSection(
                quote = uiState.quote,
                onQuoteChange = { onAction(QuoteFormAction.OnQuoteChange(quote = it)) },
                onAddByImageClick = { isOCRBottomSheetVisible = true }
            )

            if (isOCRBottomSheetVisible) {
                OCRBottomSheet(
                    onDismiss = { isOCRBottomSheetVisible = false },
                    onCameraClick = { /* TODO: 텍스트 가져오기 기능 구현 */ },
                    onGalleryClick = { /* TODO: 사진 촬영 기능 구현 */ }
                )
            }

            PageInputSection(
                page = uiState.page,
                onPageChange = { onAction(QuoteFormAction.OnPageChange(page = it)) },
                totalPage = uiState.totalPage
            )
        }
    }
}

@Composable
private fun QuoteInputSection(
    quote: String,
    onQuoteChange: (String) -> Unit,
    onAddByImageClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(And03Spacing.SPACE_S)
    ) {
        Row(
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = stringResource(R.string.add_quote_sentence_label),
                style = And03Theme.typography.labelLarge
            )

            Spacer(modifier = Modifier.weight(1f))

            AddTextByImageButton(onAddByImageClick = onAddByImageClick)
        }

        OutlinedTextField(
            value = quote,
            onValueChange = {
                if (it.length <= QuoteFormScreenValues.MAX_CHARACTER_COUNT) onQuoteChange(
                    it
                )
            },
            modifier = modifier
                .fillMaxWidth()
                .height(And03ComponentSize.TEXT_FIELD_HEIGHT_L),
            placeholder = { Text(stringResource(id = R.string.add_quote_sentence_hint)) },
            shape = And03Theme.shapes.defaultCorner
        )

        Text(
            text = stringResource(
                id = R.string.add_memo_character_count,
                quote.length,
                QuoteFormScreenValues.MAX_CHARACTER_COUNT
            ),
            modifier = Modifier.align(Alignment.End),
            style = And03Theme.typography.bodySmall,
            color = And03Theme.colors.onSurfaceVariant
        )
    }
}

@Composable
private fun PageInputSection(
    page: String,
    onPageChange: (String) -> Unit,
    totalPage: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(And03Spacing.SPACE_S)
    ) {
        Text(
            text = stringResource(R.string.add_quote_page_label),
            style = And03Theme.typography.labelLarge
        )

        OutlinedTextField(
            value = page,
            onValueChange = onPageChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    stringResource(
                        id = R.string.add_quote_page_hint,
                        totalPage,
                    )
                )
            },
            shape = And03Theme.shapes.defaultCorner,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done,
            )
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun QuoteFormScreenPreview() {
    And03Theme {
        QuoteFormScreen(
            uiState = QuoteFormUiState(
                quote = "큰 바다 넓은 하늘을 우리는 가졌노라",
                page = ""
            ),
            onAction = {}
        )
    }
}