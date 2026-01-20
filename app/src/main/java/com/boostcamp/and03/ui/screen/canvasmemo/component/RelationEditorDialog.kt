package com.boostcamp.and03.ui.screen.canvasmemo.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.boostcamp.and03.R
import com.boostcamp.and03.ui.component.And03ActionDialog
import com.boostcamp.and03.ui.component.EditableTextField
import com.boostcamp.and03.ui.theme.And03Spacing
import com.boostcamp.and03.ui.theme.And03Theme

@Composable
fun RelationEditorDialog(
    relationNameState: TextFieldState,
    fromName: String,
    toName: String,
    fromImageUrl: String = "https://i.pinimg.com/736x/8f/20/41/8f2041520696507bc2bfd2f5648c8da3.jpg",
    toImageUrl: String = "https://i.pinimg.com/736x/8f/20/41/8f2041520696507bc2bfd2f5648c8da3.jpg",
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    onFromImageClick: () -> Unit,
    onToImageClick: () -> Unit,
) {
    And03ActionDialog(
        title = stringResource(R.string.relation_dialog_title),
        dismissText = stringResource(R.string.common_cancel),
        confirmText = stringResource(R.string.common_save),
        onDismiss = onDismiss,
        onConfirm = onConfirm,
        content = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(And03Spacing.SPACE_M),
                verticalAlignment = Alignment.CenterVertically
            ) {
                PersonImagePlaceholder(
                    imageUrl = fromImageUrl,
                    onClick = onFromImageClick
                )

                Icon(
                    ImageVector.vectorResource(R.drawable.ic_rounded_arrow_forward),
                    contentDescription = null,
                    tint = And03Theme.colors.surfaceVariant
                )

                PersonImagePlaceholder(
                    imageUrl = toImageUrl,
                    onClick = onToImageClick
                )
            }

            Text(
                text = stringResource(
                    R.string.relation_dialog_question_format,
                    fromName,
                    toName
                ),
                style = And03Theme.typography.bodyMedium,
                color = And03Theme.colors.onSurface
            )

            EditableTextField(
                state = relationNameState,
                onSearch = onConfirm,
                placeholderRes = R.string.relation_name_placeholder
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun RelationEditorDialogPreview() {
    RelationEditorDialog(
        relationNameState = TextFieldState(),
        fromName = "이름1",
        toName = "이름2",
        onDismiss = {},
        onConfirm = {},
        onFromImageClick = {},
        onToImageClick = {},
    )
}