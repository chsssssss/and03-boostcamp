package com.boostcamp.and03.ui.screen.canvasmemo.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.boostcamp.and03.ui.theme.And03Padding
import com.boostcamp.and03.ui.theme.And03Radius
import com.boostcamp.and03.ui.theme.And03Theme

data class AlertAction(
    val text: String,
    val onClick: () -> Unit,
)

@Composable
fun AlertMessageCard(
    message: String,
    modifier: Modifier = Modifier,
    actions: List<AlertAction> = emptyList()
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = And03Theme.colors.onSurfaceVariant,
                shape = RoundedCornerShape(And03Radius.RADIUS_S)
            )
            .background(
                color = And03Theme.colors.surface,
                shape = RoundedCornerShape(And03Radius.RADIUS_S)
            )
            .padding(And03Padding.PADDING_M),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = message,
            color = And03Theme.colors.onSurfaceVariant,
        )

        if (actions.isNotEmpty()) {
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                actions.forEach { action ->
                    TextButton(onClick = action.onClick) {
                        Text(
                            text = action.text,
                            color = And03Theme.colors.primary
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AlertMessageCardPreview() {
    AlertMessageCard(
        message = "삭제할 아이템을 선택해주세요.",
        actions = listOf(
            AlertAction("취소") { },
            AlertAction("삭제") { }
        )
    )
}