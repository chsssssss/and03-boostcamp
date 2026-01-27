package com.boostcamp.and03.ui.screen.canvasmemo.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldBuffer
import androidx.compose.foundation.text.input.TextFieldState
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
import com.boostcamp.and03.R
import com.boostcamp.and03.ui.component.AddTextByImageButton
import com.boostcamp.and03.ui.component.And03ActionDialog
import com.boostcamp.and03.ui.component.EditableTextField
import com.boostcamp.and03.ui.component.LabelAndEditableTextField
import com.boostcamp.and03.ui.component.OCRBottomSheet
import com.boostcamp.and03.ui.theme.And03ComponentSize
import com.boostcamp.and03.ui.theme.And03Spacing
import com.boostcamp.and03.ui.theme.And03Theme

private object AddQuoteDialogValues {
    const val MAX_CHARACTER_COUNT = 500
}

private object DigitOnlyTransformation : InputTransformation {
    override fun TextFieldBuffer.transformInput() {
        val currentInputText = toString()
        val filteredText = currentInputText.filter { it.isDigit() }

        if (currentInputText != filteredText) {
            replace(
                start = 0,
                end = length,
                text = filteredText
            )
        }
    }
}

@Composable
fun AddQuoteDialog(
    quoteState: TextFieldState,
    pageState: TextFieldState,
    enabled: Boolean = false,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    var isOCRBottomSheetVisible by remember { mutableStateOf(false) }

    And03ActionDialog(
        title = stringResource(id = R.string.add_quote_title),
        dismissText = stringResource(id = R.string.common_cancel),
        confirmText = stringResource(id = R.string.common_save),
        onDismiss = onDismiss,
        onConfirm = onConfirm,
        enabled = enabled,
        content = {
            QuoteInputSection(
                quoteState = quoteState,
                onAddByImageClick = { isOCRBottomSheetVisible = true }
            )

            if (isOCRBottomSheetVisible) {
                OCRBottomSheet(
                    onDismiss = { isOCRBottomSheetVisible = false },
                    onCameraClick = { /* TODO: 텍스트 가져오기 기능 구현 */ },
                    onGalleryClick = { /* TODO: 사진 촬영 기능 구현 */ }
                )
            }

            LabelAndEditableTextField(
                labelRes = R.string.add_quote_page_label,
                state = pageState,
                placeholderRes = R.string.add_quote_page_hint,
                onSubmit = {},
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Number,
                inputTransformation = DigitOnlyTransformation
            )
        }
    )
}

@Composable
private fun QuoteInputSection(
    quoteState: TextFieldState,
    onAddByImageClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
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

        EditableTextField(
            state = quoteState,
            placeholderRes = R.string.add_quote_sentence_hint,
            onSubmit = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(And03ComponentSize.TEXT_FIELD_HEIGHT_L),
            maxCharacterCount = AddQuoteDialogValues.MAX_CHARACTER_COUNT,
            lineLimits = AddQuoteDialogValues.MAX_CHARACTER_COUNT
        )

        Text(
            text = stringResource(
                id = R.string.add_quote_text_count,
                quoteState.text.length,
                AddQuoteDialogValues.MAX_CHARACTER_COUNT
            ),
            modifier = Modifier.align(Alignment.End),
            style = And03Theme.typography.bodySmall,
            color = And03Theme.colors.onSurfaceVariant
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AddQuoteDialogPreview() {
    And03Theme {
        AddQuoteDialog(
            quoteState = TextFieldState(),
            pageState = TextFieldState(),
            onDismiss = {},
            onConfirm = {}
        )
    }
}