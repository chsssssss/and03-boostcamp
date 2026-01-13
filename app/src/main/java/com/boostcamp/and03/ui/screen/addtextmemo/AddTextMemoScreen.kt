package com.boostcamp.and03.ui.screen.addtextmemo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.boostcamp.and03.R
import com.boostcamp.and03.ui.component.And03AppBar
import com.boostcamp.and03.ui.component.And03Button
import com.boostcamp.and03.ui.component.ButtonVariant
import com.boostcamp.and03.ui.component.LabelAndEditableTextField
import com.boostcamp.and03.ui.theme.And03ComponentSize
import com.boostcamp.and03.ui.theme.And03IconSize
import com.boostcamp.and03.ui.theme.And03Padding
import com.boostcamp.and03.ui.theme.And03Radius
import com.boostcamp.and03.ui.theme.And03Spacing
import com.boostcamp.and03.ui.theme.And03Theme

private object AddTextMemoScreenValues {
    const val MAX_CHARACTER_COUNT = 500
}

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
        onContentChange = { viewModel.updateContent(uiState.content) }
    )
}

@Composable
private fun AddTextMemoScreen(
    uiState: AddTextMemoUiState,
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit,
    onTitleChange: (String) -> Unit,
    onContentChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            And03AppBar(
                title = stringResource(id = R.string.add_memo_text_app_bar_title),
                onBackClick = onBackClick,
                actions = {
                    IconButton(
                        onClick = onSaveClick,
                        enabled = uiState.isSaveable
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_check_filled),
                            contentDescription = stringResource(R.string.content_description_save_button)
                        )
                    }
                }
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
            LabelAndEditableTextField(
                labelRes = R.string.add_memo_title,
                placeholderRes = R.string.add_memo_enter_title_placeholder,
                state = TextFieldState(),
                onSearch = {}
            )

            ContentInputSection(
                value = uiState.content,
                onValueChange = onContentChange
            )
        }
    }
}

@Composable
private fun ContentInputSection(
    value: String,
    onValueChange: (String) -> Unit,
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
                text = stringResource(R.string.add_memo_content),
                style = And03Theme.typography.labelLarge
            )

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier
                    .background(
                        color = And03Theme.colors.surfaceVariant,
                        shape = RoundedCornerShape(And03Radius.RADIUS_S)
                    )
                    .padding(horizontal = And03Padding.PADDING_S, vertical = And03Padding.PADDING_XS),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.CameraAlt,
                    contentDescription = stringResource(R.string.content_description_take_a_photo),
                    tint = And03Theme.colors.onSurfaceVariant,
                    modifier = Modifier.size(And03IconSize.ICON_SIZE_S)
                )

                Spacer(modifier = Modifier.width(And03Spacing.SPACE_XS))

                Text(
                    text = stringResource(R.string.add_memo_add_by_image),
                    style = And03Theme.typography.bodySmall,
                    color = And03Theme.colors.onSurfaceVariant
                )
            }
        }

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier
                .fillMaxWidth()
                .height(And03ComponentSize.TEXT_FIELD_HEIGHT_L),
            placeholder = { Text(stringResource(id = R.string.add_memo_text_enter_content_placeholder)) },
            shape = And03Theme.shapes.defaultCorner
        )

        Text(
            text = stringResource(
                id = R.string.add_memo_character_count,
                value.length,
                AddTextMemoScreenValues.MAX_CHARACTER_COUNT
            ),
            modifier = Modifier.align(Alignment.End),
            style = And03Theme.typography.bodySmall,
            color = And03Theme.colors.onSurfaceVariant
        )
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
            onContentChange = {}
        )
    }
}