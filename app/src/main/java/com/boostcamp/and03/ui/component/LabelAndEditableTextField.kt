package com.boostcamp.and03.ui.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.boostcamp.and03.R
import com.boostcamp.and03.ui.theme.And03Radius
import com.boostcamp.and03.ui.theme.And03Spacing

@Composable
fun LabelAndEditableTextField(
    labelRes: Int,
    state: TextFieldState,
    onSearch: () -> Unit,
    modifier: Modifier = Modifier,
    @StringRes placeholderRes: Int = R.string.editable_text_field_hint,
    imeAction: ImeAction = ImeAction.Next,
    keyboardType: KeyboardType = KeyboardType.Text,
    maxCharacterCount: Int = 10,
    lineLimits: Int = 1,
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(labelRes),
            style = MaterialTheme.typography.labelLarge,
        )

        Spacer(modifier = Modifier.height(And03Spacing.SPACE_S))

        EditableTextField(
            state = state,
            onSearch = onSearch,
            placeholderRes = placeholderRes,
            imeAction = imeAction,
            keyboardType = keyboardType,
            maxCharacterCount = maxCharacterCount,
            lineLimits = lineLimits,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LabelAndTextFieldPreview() {
    LabelAndEditableTextField(
        labelRes = R.string.editable_text_field_hint,
        state = TextFieldState(),
        onSearch = {}
    )
}