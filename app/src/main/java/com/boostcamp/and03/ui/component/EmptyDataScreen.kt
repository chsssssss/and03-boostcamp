package com.boostcamp.and03.ui.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.boostcamp.and03.R
import com.boostcamp.and03.ui.theme.And03IconSize
import com.boostcamp.and03.ui.theme.And03Padding
import com.boostcamp.and03.ui.theme.And03Spacing
import com.boostcamp.and03.ui.theme.And03Theme

@Composable
fun EmptyDataScreen(
    @StringRes message: Int = R.string.empty_message,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = And03Padding.PADDING_2XL),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_outline_inbox),
            contentDescription = null,
            tint = And03Theme.colors.onSurfaceVariant,
            modifier = Modifier.size(And03IconSize.ICON_SIZE_L)
        )

        Spacer(modifier = Modifier.height(And03Spacing.SPACE_M))

        Text(
            text = stringResource(message),
            style = And03Theme.typography.bodyMedium,
            color = And03Theme.colors.onSurfaceVariant
        )
    }
}
