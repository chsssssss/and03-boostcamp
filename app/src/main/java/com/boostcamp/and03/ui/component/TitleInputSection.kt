package com.boostcamp.and03.ui.component

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.boostcamp.and03.R

@Composable
fun TitleInputSection(
    title: String,
    onTitleChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val titleTextFieldState = remember { TextFieldState(title) }

    LabelAndEditableTextField(
        labelRes = R.string.add_memo_title,
        placeholderRes = R.string.add_memo_enter_title_placeholder,
        state = titleTextFieldState,
        onSearch = {},
        modifier = modifier
    )
}