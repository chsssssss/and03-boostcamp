package com.boostcamp.and03.ui.screen.canvasmemo.component

import androidx.compose.ui.graphics.Color
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
import com.boostcamp.and03.data.model.request.ProfileType
import com.boostcamp.and03.ui.component.And03ActionDialog
import com.boostcamp.and03.ui.component.EditableTextField
import com.boostcamp.and03.ui.theme.And03Spacing
import com.boostcamp.and03.ui.theme.And03Theme

@Composable
fun RelationEditorDialog(
    relationNameState: TextFieldState,

    fromName: String,
    toName: String,

    fromProfileType: ProfileType,
    fromImageUrl: String,
    fromIconColor: Color,

    toProfileType: ProfileType,
    toImageUrl: String,
    toIconColor: Color,

    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
//    onFromImageClick: () -> Unit,
//    onToImageClick: () -> Unit,
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
                    profileType = fromProfileType,
                    imageUrl = fromImageUrl,
                    iconColor = fromIconColor,
                )

                Icon(
                    ImageVector.vectorResource(R.drawable.ic_rounded_arrow_forward),
                    contentDescription = null,
                    tint = And03Theme.colors.surfaceVariant
                )

                PersonImagePlaceholder(
                    profileType = toProfileType,
                    imageUrl = toImageUrl,
                    iconColor = toIconColor,
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
                onSubmit = onConfirm,
                placeholderRes = R.string.relation_name_placeholder,
                maxCharacterCount = 30
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
        fromImageUrl = "",
        toImageUrl = "",
        fromProfileType = ProfileType.COLOR,
        toProfileType = ProfileType.COLOR,
        fromIconColor = Color(0xFF1E88E5),
        toIconColor = Color(0xFF1E88E5),
        onDismiss = {},
        onConfirm = {},
    )
}