package com.boostcamp.and03.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import com.boostcamp.and03.ui.theme.And03Padding
import com.boostcamp.and03.ui.theme.And03Spacing
import com.boostcamp.and03.ui.theme.And03Theme

@Composable
fun And03ActionDialog(
    title: String,
    dismissText: String,
    confirmText: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    content: @Composable ColumnScope.() -> Unit,
    modifier: Modifier = Modifier,
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = And03Theme.shapes.dialogCorner,
            color = And03Theme.colors.surface
        ) {
            Column(
                modifier = modifier.padding(And03Padding.PADDING_L),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(And03Spacing.SPACE_L)
            ) {
                Text(
                    text = title,
                    style = And03Theme.typography.titleMedium,
                    color = And03Theme.colors.onSurface
                )

                content()

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
fun And03ActionDialogPreview() {
    And03ActionDialog(
        title = "등장인물 추가",
        dismissText = "취소",
        confirmText = "저장",
        onDismiss = {},
        onConfirm = {},
        content = {}
    )
}