package com.boostcamp.and03.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.boostcamp.and03.R
import com.boostcamp.and03.ui.theme.And03Padding
import com.boostcamp.and03.ui.theme.And03Spacing
import com.boostcamp.and03.ui.theme.And03Theme

enum class DialogDismissAction {
    Dismiss,
    Confirm
}

@Composable
fun And03Dialog(
    @DrawableRes iconResId: Int,
    iconColor: Color,
    iconContentDescription: String,
    title: String,
    dismissText: String,
    confirmText: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier,
    description: String = "",
    dismissAction: DialogDismissAction = DialogDismissAction.Dismiss,
) {
    Dialog(
        onDismissRequest = {
            when (dismissAction) {
                DialogDismissAction.Dismiss -> onDismiss()
                DialogDismissAction.Confirm -> onConfirm()
            }
        },
    ) {
        Surface(
            shape = And03Theme.shapes.dialogCorner,
            color = And03Theme.colors.surface
        ) {
            Column(
                modifier = modifier.padding(And03Padding.PADDING_L),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(And03Spacing.SPACE_L)
            ) {
                IconBadge(
                    iconResId = iconResId,
                    iconColor = iconColor,
                    contentDescription = iconContentDescription
                )

                Text(
                    text = title,
                    style = And03Theme.typography.titleMedium,
                    color = And03Theme.colors.onSurface
                )

                if (description.isNotBlank()) {
                    Text(
                        text = description,
                        style = And03Theme.typography.bodyMedium,
                        color = And03Theme.colors.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(And03Spacing.SPACE_M),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    And03Button(
                        text = dismissText,
                        onClick = onDismiss,
                        variant = ButtonVariant.SurfaceVariant,
                        modifier = Modifier.weight(1f)
                    )

                    And03Button(
                        text = confirmText,
                        onClick = onConfirm,
                        variant = ButtonVariant.Primary,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun And03DialogPreview() {
    And03Dialog(
        iconResId = R.drawable.ic_warning_filled,
        iconColor = And03Theme.colors.error,
        iconContentDescription = "아이콘 설명",
        title = "변경사항이 저장되지 않았어요",
        dismissText = "나가기",
        confirmText = "계속 편집",
        onDismiss = {},
        onConfirm = {},
        description = "나가면 입력한 내용이 사라집니다."
    )
}