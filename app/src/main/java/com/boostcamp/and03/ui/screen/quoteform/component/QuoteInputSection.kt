package com.boostcamp.and03.ui.screen.quoteform.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.boostcamp.and03.R
import com.boostcamp.and03.ui.theme.And03ComponentSize
import com.boostcamp.and03.ui.theme.And03IconSize
import com.boostcamp.and03.ui.theme.And03Spacing
import com.boostcamp.and03.ui.theme.And03Theme

@Composable
fun AddQuoteInputSection() {
    Column {
        QuoteHeader()

        Spacer(modifier = Modifier.height(And03Spacing.SPACE_S))

        QuoteTextField()

        Spacer(modifier = Modifier.height(And03Spacing.SPACE_XS))

        Text(
            text = stringResource(R.string.add_quote_text_count, 0, 500),
            modifier = Modifier.align(Alignment.End),
            style = And03Theme.typography.bodySmall,
            color = And03Theme.colors.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(And03Spacing.SPACE_XL))

        PageSection()
    }
}


@Composable
private fun QuoteHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.add_quote_sentence_label),
            style = And03Theme.typography.titleMedium
        )

        Spacer(modifier = Modifier.weight(1f))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.CameraAlt,
                contentDescription = stringResource(R.string.content_description_take_a_photo),
                tint = And03Theme.colors.onSurfaceVariant,
                modifier = Modifier.size(And03IconSize.ICON_SIZE_S)
            )
            Spacer(modifier = Modifier.width(And03Spacing.SPACE_XS))
            Text(
                text = stringResource(R.string.add_quote_add_by_image),
                style = And03Theme.typography.bodySmall,
                color = And03Theme.colors.onSurfaceVariant
            )
        }
    }
}


@Composable
private fun QuoteTextField() {
    OutlinedTextField(
        value = "",
        onValueChange = {},
        modifier = Modifier
            .fillMaxWidth()
            .height(And03ComponentSize.TEXT_FIELD_HEIGHT_L),
        placeholder = {
            Text(stringResource(R.string.add_quote_sentence_hint))
        },
        shape = And03Theme.shapes.defaultCorner
    )
}


@Composable
private fun PageSection() {
    Text(
        text = stringResource(R.string.add_quote_page_label),
        style = And03Theme.typography.titleMedium
    )

    Spacer(modifier = Modifier.height(And03Spacing.SPACE_S))

    OutlinedTextField(
        value = "",
        onValueChange = {},
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(stringResource(R.string.add_quote_page_hint))},
        shape = And03Theme.shapes.defaultCorner
    )
}

@Preview(showBackground = true)
@Composable
private fun AddQuoteInputSectionPreview() {
    And03Theme {
        AddQuoteInputSection()
    }
}
