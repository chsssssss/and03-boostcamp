package com.boostcamp.and03.ui.screen.addquote.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.boostcamp.and03.R
import com.boostcamp.and03.ui.theme.*

@Composable
fun AddQuoteInfoSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = And03Theme.colors.primaryContainer,
                shape = RoundedCornerShape(And03Radius.RADIUS_M)
            )
            .border(
                width = And03BorderWidth.BORDER_1,
                color = And03Theme.colors.primary,
                shape = RoundedCornerShape(And03Radius.RADIUS_M)
            )
            .padding(And03Padding.PADDING_L),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Lightbulb,
            contentDescription = null,
            tint = And03Theme.colors.primary,
            modifier = Modifier.size(And03IconSize.ICON_SIZE_S)
        )

        Spacer(modifier = Modifier.width(And03Spacing.SPACE_M))

        Column {
            Text(
                text = stringResource(R.string.add_quote_info_title),
                style = And03Theme.typography.bodyMedium,
                color = And03Theme.colors.primary
            )
            Text(
                text = stringResource(R.string.add_quote_info_description),
                style = And03Theme.typography.bodySmall,
                color = And03Theme.colors.primary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AddQuoteInfoSectionPreview() {
    And03Theme {
        AddQuoteInfoSection()
    }
}
