package com.boostcamp.and03.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.boostcamp.and03.R
import com.boostcamp.and03.ui.theme.And03ComponentSize
import com.boostcamp.and03.ui.theme.And03Spacing
import com.boostcamp.and03.ui.theme.And03Theme

private object ContentInputSectionValues {
    const val MAX_CHARACTER_COUNT = 500
}

@Composable
fun ContentInputSection(
    label: String,
    content: String,
    onContentChange: (String) -> Unit,
    onAddByImageClick: () -> Unit,
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
                text = label,
                style = And03Theme.typography.labelLarge
            )

            Spacer(modifier = Modifier.weight(1f))

            AddTextByImageButton(onAddByImageClick = onAddByImageClick)
        }

        OutlinedTextField(
            value = content,
            onValueChange = { if (it.length <= ContentInputSectionValues.MAX_CHARACTER_COUNT) onContentChange(it) },
            modifier = modifier
                .fillMaxWidth()
                .height(And03ComponentSize.TEXT_FIELD_HEIGHT_L),
            placeholder = { Text(stringResource(id = R.string.add_memo_text_enter_content_placeholder)) },
            shape = And03Theme.shapes.defaultCorner
        )

        Text(
            text = stringResource(
                id = R.string.add_memo_character_count,
                content.length,
                ContentInputSectionValues.MAX_CHARACTER_COUNT
            ),
            modifier = Modifier.align(Alignment.End),
            style = And03Theme.typography.bodySmall,
            color = And03Theme.colors.onSurfaceVariant
        )
    }
}