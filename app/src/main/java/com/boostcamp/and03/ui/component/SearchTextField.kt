package com.boostcamp.and03.ui.component

import androidx.annotation.StringRes
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.maxLength
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.boostcamp.and03.R

@Composable
fun SearchTextField(
    state: TextFieldState,
    onSearch: () -> Unit,
    modifier: Modifier = Modifier,
    @StringRes placeholderRes: Int = R.string.search_text_field_hint,
    enableCameraSearch: Boolean = false,
    onCameraClick: (() -> Unit)? = null,
) {
    TextField(
        state = state,
        modifier = modifier,
        placeholder = { Text(text = stringResource(id = placeholderRes)) },
        leadingIcon = {
            Icon(
                ImageVector.vectorResource(R.drawable.ic_rounded_search),
                contentDescription = null,
            )
        },
        trailingIcon = {
            ClearOrCameraIcon(
                hasText = state.text.isNotEmpty(),
                enableCameraSearch = enableCameraSearch,
                onClear = { state.clearText() },
                onCameraClick = onCameraClick
            )
        },
        keyboardOptions = KeyboardOptions(
            autoCorrectEnabled = true,  // 키보드 자동 수정 기능
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        onKeyboardAction = {
            onSearch()
        },
        lineLimits = TextFieldLineLimits.SingleLine,
        shape = RoundedCornerShape(12.dp),
        colors = TextFieldDefaults.colors(
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            focusedLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            unfocusedLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            focusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,
            unfocusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,
            disabledIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,
        ),
    )
}

@Composable
private fun ClearOrCameraIcon(
    hasText: Boolean,
    enableCameraSearch: Boolean,
    onClear: () -> Unit,
    onCameraClick: (() -> Unit)?
) {
    when {
        hasText -> {
            IconButton(onClick = onClear) {
                Icon(
                    ImageVector.vectorResource(R.drawable.ic_baseline_cancel),
                    contentDescription = null
                )
            }
        }

        enableCameraSearch -> {
            IconButton(onClick = { onCameraClick?.invoke() }) {
                Icon(
                    ImageVector.vectorResource(R.drawable.ic_round_camera_alt),
                    contentDescription = null
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SearchTextFieldPreview() {
    SearchTextField(
        state = TextFieldState(),
        onSearch = {},
        modifier = Modifier,
    )
}