package com.boostcamp.and03.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.boostcamp.and03.R
import com.boostcamp.and03.ui.theme.And03Padding
import com.boostcamp.and03.ui.theme.And03Spacing
import com.boostcamp.and03.ui.theme.And03Theme

private object PhotoPickerBottomSheetValues {
    val GalleryIconColor = Color(0xFF16A34A)
    val RightArrowIconSize = 28.dp
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoPickerBottomSheet(
    onDismiss: () -> Unit,
    onCameraClick: () -> Unit,
    onGalleryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        dragHandle = null,
        containerColor = And03Theme.colors.surface,
        scrimColor = And03Theme.colors.scrim
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(And03Padding.PADDING_L),
            verticalArrangement = Arrangement.spacedBy(And03Spacing.SPACE_L)
        ) {

            OCRBottomSheetButton(
                iconResId = R.drawable.ic_round_camera_alt,
                iconColor = And03Theme.colors.primary,
                contentDescription = "",
                title = stringResource(R.string.img_picker_bottom_sheet_take_a_photo),
                description = stringResource(R.string.img_picker_sheet_take_a_photo_description),
                onClick = onCameraClick
            )

            OCRBottomSheetButton(
                iconResId = R.drawable.ic_photo_library_filled,
                iconColor = PhotoPickerBottomSheetValues.GalleryIconColor,
                contentDescription = "",
                title = stringResource(R.string.img_picker_sheet_select_image),
                description = stringResource(R.string.img_picker_sheet_select_image_description),
                onClick = onGalleryClick
            )
        }
    }
}

@Composable
private fun OCRBottomSheetButton(
    @DrawableRes iconResId: Int,
    iconColor: Color,
    contentDescription: String,
    title: String,
    description: String,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = And03Theme.shapes.defaultCorner,
        border = BorderStroke(
            1.dp,
            And03Theme.colors.outline
        ),
        color = And03Theme.colors.surface,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(And03Padding.PADDING_L),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(And03Spacing.SPACE_M)
        ) {
            IconBadge(
                iconResId = iconResId,
                iconColor = iconColor,
                contentDescription = contentDescription
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = And03Theme.typography.bodyLarge
                )

                Text(
                    text = description,
                    style = And03Theme.typography.bodySmall,
                    color = And03Theme.colors.onSurfaceVariant
                )
            }

            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_keyboard_arrow_right_filled),
                contentDescription = null,
                modifier = Modifier.size(PhotoPickerBottomSheetValues.RightArrowIconSize),
                tint = And03Theme.colors.onSurfaceVariant
            )
        }
    }
}

@Preview
@Composable
private fun PhotoPickerBottomSheet() {
    And03Theme {
        PhotoPickerBottomSheet(
            onDismiss = {},
            onCameraClick = {},
            onGalleryClick = {}
        )
    }
}
