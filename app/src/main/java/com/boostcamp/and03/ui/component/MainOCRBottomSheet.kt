package com.boostcamp.and03.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.boostcamp.and03.ui.theme.Dimensions
import com.boostcamp.and03.R
import com.boostcamp.and03.ui.theme.MainTheme

private object MainOCRBottomSheetValues {
    val GalleryIconColor = Color(0xFF16A34A)
    val RightArrowIconSize = 28.dp
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainOCRBottomSheet(
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
        containerColor = MainTheme.colors.surface,
        scrimColor = MainTheme.colors.scrim
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(Dimensions.PADDING_L),
            verticalArrangement = Arrangement.spacedBy(Dimensions.PADDING_L)
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.ocr_bottom_sheet_import_text),
                    style = MainTheme.typography.titleLarge,
                    modifier = Modifier.align(Alignment.Center)
                )

                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_close_filled),
                        contentDescription = stringResource(R.string.content_description_close)
                    )
                }
            }

            OCRBottomSheetButton(
                iconResId = R.drawable.ic_round_camera_alt,
                iconColor = MainTheme.colors.primary,
                title = stringResource(R.string.ocr_bottom_sheet_take_a_photo),
                description = stringResource(R.string.ocr_bottom_sheet_take_a_photo_description),
                onClick = onCameraClick
            )

            OCRBottomSheetButton(
                iconResId = R.drawable.ic_photo_library_filled,
                iconColor = MainOCRBottomSheetValues.GalleryIconColor,
                title = stringResource(R.string.ocr_bottom_sheet_select_image),
                description = stringResource(R.string.ocr_bottom_sheet_select_image_description),
                onClick = onGalleryClick
            )
        }
    }
}

@Composable
private fun OCRBottomSheetButton(
    @DrawableRes iconResId: Int,
    iconColor: Color,
    title: String,
    description: String,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = MainTheme.shapes.defaultCorner,
        border = BorderStroke(1.dp, MainTheme.colors.outline),
        color = MainTheme.colors.surface,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(Dimensions.PADDING_L),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Dimensions.PADDING_M)
        ) {
            MainIconItem(
                iconResId = iconResId,
                iconColor = iconColor,
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = MainTheme.typography.bodyLarge
                )

                Text(
                    text = description,
                    style = MainTheme.typography.bodySmall,
                    color = MainTheme.colors.onSurfaceVariant
                )
            }

            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_keyboard_arrow_right_filled),
                contentDescription = null,
                modifier = Modifier.size(MainOCRBottomSheetValues.RightArrowIconSize),
                tint = MainTheme.colors.onSurfaceVariant
            )
        }
    }
}

@Preview
@Composable
private fun MainOCRBottomSheetPreview() {
    MainTheme {
        MainOCRBottomSheet(
            onDismiss = {},
            onCameraClick = {},
            onGalleryClick = {}
        )
    }
}