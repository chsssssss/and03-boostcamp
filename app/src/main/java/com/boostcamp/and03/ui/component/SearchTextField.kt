package com.boostcamp.and03.ui.component

import androidx.annotation.StringRes
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.boostcamp.and03.R
import com.boostcamp.and03.ui.theme.MainTheme

@Composable
fun SearchTextField(
    state: TextFieldState,
    onSearch: () -> Unit,
    modifier: Modifier = Modifier,
    @StringRes placeholderRes: Int = R.string.search_text_field_hint,
    enableCameraSearch: Boolean = false,
    onCameraClick: () -> Unit = {},
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
        onKeyboardAction = { onSearch() },
        lineLimits = TextFieldLineLimits.SingleLine,
        shape = MainTheme.shapes.searchTextFieldCorner,
        colors = TextFieldDefaults.colors(
            focusedTextColor = MainTheme.colors.onSurface,
            unfocusedTextColor = MainTheme.colors.onSurface,
            focusedContainerColor = MainTheme.colors.surfaceVariant,
            unfocusedContainerColor = MainTheme.colors.surfaceVariant,
            focusedLeadingIconColor = MainTheme.colors.onSurfaceVariant,
            unfocusedLeadingIconColor = MainTheme.colors.onSurfaceVariant,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}

@Composable
private fun ClearOrCameraIcon(
    hasText: Boolean,
    enableCameraSearch: Boolean,
    onClear: () -> Unit,
    onCameraClick: () -> Unit = {}
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
            IconButton(onClick = onCameraClick) {
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
    MainTheme {
        SearchTextField(
            state = TextFieldState(),
            onSearch = {},
            modifier = Modifier,
        )
    }
}