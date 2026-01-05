package com.boostcamp.and03.ui.component

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.maxLength
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.boostcamp.and03.R

@Composable
fun EditableTextField(
    state: TextFieldState,
    onSearch: () -> Unit,
    modifier: Modifier = Modifier,
    placeholderRes: Int = R.string.editable_text_field_hint,
    imeAction: ImeAction = ImeAction.Next,
    keyboardType: KeyboardType = KeyboardType.Text,
    maxCharacterCount: Int = 10,
    lineLimits: Int = 1,
) {
    OutlinedTextField(
        state = state,
        modifier = modifier,
        placeholder = { Text(text = stringResource(placeholderRes)) },
        inputTransformation = InputTransformation.maxLength(maxCharacterCount),
        keyboardOptions = KeyboardOptions(
            autoCorrectEnabled = true,  // 키보드 자동 수정 기능
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        onKeyboardAction = { onSearch() },
        lineLimits = TextFieldLineLimits.MultiLine(maxHeightInLines = lineLimits),
        shape = RoundedCornerShape(12.dp),
        colors = TextFieldDefaults.colors(
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedIndicatorColor = MaterialTheme.colorScheme.outline,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.outline,
            disabledIndicatorColor = MaterialTheme.colorScheme.outline,
        ),
    )
}

@Preview(showBackground = true)
@Composable
fun EditableTextFieldPreview() {
    EditableTextField(
        state = TextFieldState(),
        onSearch = {},
        modifier = Modifier,
    )

}