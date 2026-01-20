package com.boostcamp.and03.ui.screen.canvasmemo.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.boostcamp.and03.R
import com.boostcamp.and03.ui.theme.And03Padding
import com.boostcamp.and03.ui.theme.And03Spacing

data class ToolAction(
    val iconRes: Int,
    val contentDescription: String?,
    val onClick: () -> Unit,
)

@Composable
fun ToolExpandableButton(
    modifier: Modifier = Modifier,
    actions: List<ToolAction>,
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .padding(And03Padding.PADDING_M),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(
            visible = expanded,
            enter = fadeIn() + slideInVertically { it / 2 },
            exit = fadeOut() + slideOutVertically { it / 2 },
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(And03Spacing.SPACE_S),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                actions.forEach { action ->
                    ToolIconButton(
                        iconRes = action.iconRes,
                        contentDescription = action.contentDescription,
                        onClick = action.onClick
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(And03Spacing.SPACE_M))

        ToolIconButton(
            iconRes = if (expanded) {
                R.drawable.ic_close_filled
            } else {
                R.drawable.ic_round_build
            },
            contentDescription = null,
            onClick = { expanded = !expanded }
        )
    }

}

