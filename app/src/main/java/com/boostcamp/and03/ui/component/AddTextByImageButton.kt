package com.boostcamp.and03.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.boostcamp.and03.R
import com.boostcamp.and03.ui.theme.And03IconSize
import com.boostcamp.and03.ui.theme.And03Padding
import com.boostcamp.and03.ui.theme.And03Radius
import com.boostcamp.and03.ui.theme.And03Spacing
import com.boostcamp.and03.ui.theme.And03Theme

@Composable
fun AddTextByImageButton(
    onAddByImageClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(
                color = And03Theme.colors.surfaceVariant,
                shape = RoundedCornerShape(And03Radius.RADIUS_S)
            )
            .padding(horizontal = And03Padding.PADDING_S, vertical = And03Padding.PADDING_XS)
            .clickable { onAddByImageClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.CameraAlt,
            contentDescription = stringResource(R.string.content_description_take_a_photo),
            tint = And03Theme.colors.onSurfaceVariant,
            modifier = Modifier.size(And03IconSize.ICON_SIZE_S)
        )

        Spacer(modifier = Modifier.width(And03Spacing.SPACE_XS))

        Text(
            text = stringResource(R.string.add_memo_add_by_image),
            style = And03Theme.typography.bodySmall,
            color = And03Theme.colors.onSurfaceVariant
        )
    }
}