package com.boostcamp.and03.ui.screen.canvasmemo.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.boostcamp.and03.R
import com.boostcamp.and03.ui.component.And03ActionDialog
import com.boostcamp.and03.ui.component.LabelAndEditableTextField
import com.boostcamp.and03.ui.theme.And03Spacing

@Composable
fun AddCharacterDialog(
    nameState: TextFieldState,
    descState: TextFieldState,
    enabled: Boolean = true,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    onClickAddImage: () -> Unit,
) {
    And03ActionDialog(
        title = stringResource(R.string.add_character_dialog_title),
        dismissText = stringResource(R.string.common_cancel),
        confirmText = stringResource(R.string.common_save),
        onDismiss = onDismiss,
        onConfirm = onConfirm,
        enabled = enabled,
        content = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(And03Spacing.SPACE_M),
                verticalAlignment = Alignment.CenterVertically
            ) {
                PersonImagePlaceholder(
                    imageUrl = null,
                    onClick = onClickAddImage
                )
            }

            LabelAndEditableTextField(
                labelRes = R.string.add_character_dialog_name_label,
                state = nameState,
                placeholderRes = R.string.add_character_dialog_name_placeholder,
                onSubmit = {},
            )

            LabelAndEditableTextField(
                labelRes = R.string.add_character_dialog_desc_label,
                state = descState,
                placeholderRes = R.string.add_character_dialog_desc_placeholder,
                onSubmit = {},
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun AddCharacterDialogPreview() {
    AddCharacterDialog(
        nameState = TextFieldState(),
        descState = TextFieldState(),
        onDismiss = {},
        onConfirm = {},
        onClickAddImage = {},
    )
}