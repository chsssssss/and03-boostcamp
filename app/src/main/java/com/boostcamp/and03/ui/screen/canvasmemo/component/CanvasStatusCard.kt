package com.boostcamp.and03.ui.screen.canvasmemo.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.ZoomIn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.boostcamp.and03.R
import com.boostcamp.and03.ui.theme.And03Elevation
import com.boostcamp.and03.ui.theme.And03IconSize
import com.boostcamp.and03.ui.theme.And03Padding
import com.boostcamp.and03.ui.theme.And03Radius
import com.boostcamp.and03.ui.theme.And03Spacing
import com.boostcamp.and03.ui.theme.And03Theme

@Composable
fun CanvasStatusCard(
    canvasViewOffset: Offset,
    zoomScale: Float,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = And03Theme.colors.surfaceVariant
        ),
        shape = RoundedCornerShape(And03Radius.RADIUS_S),
        elevation = CardDefaults.cardElevation(defaultElevation = And03Elevation.S)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = And03Padding.PADDING_M, vertical = And03Padding.PADDING_S),
            verticalArrangement = Arrangement.spacedBy(And03Spacing.SPACE_S)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(And03Spacing.SPACE_S)
            ) {
                Icon(
                    imageVector = Icons.Default.MyLocation,
                    contentDescription = stringResource(R.string.content_description_current_coordinate),
                    tint = And03Theme.colors.primary,
                    modifier = Modifier.size(And03IconSize.ICON_SIZE_XS)
                )

                Text(
                    text = stringResource(R.string.content_description_current_coordinate),
                    style = MaterialTheme.typography.labelSmall,
                    color = And03Theme.colors.onSurfaceVariant
                )


                Text(
                    text = stringResource(
                        R.string.canvas_offset_format,
                        canvasViewOffset.x,
                        canvasViewOffset.y
                    ),
                    style = MaterialTheme.typography.bodySmall,
                    color = And03Theme.colors.onSurface
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(And03Spacing.SPACE_S)
            ) {
                Icon(
                    imageVector = Icons.Default.ZoomIn,
                    contentDescription = stringResource(R.string.content_description_zoom_scale),
                    tint = And03Theme.colors.primary,
                    modifier = Modifier.size(And03IconSize.ICON_SIZE_XS)
                )

                Text(
                    text = stringResource(R.string.content_description_zoom_scale),
                    style = MaterialTheme.typography.labelSmall,
                    color = And03Theme.colors.onSurfaceVariant
                )

                Text(
                    text = stringResource(
                        R.string.percentage_value,
                        (zoomScale * 100).toInt()
                    ),
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.SemiBold,
                    color = And03Theme.colors.onSurface
                )
            }
        }
    }
}
