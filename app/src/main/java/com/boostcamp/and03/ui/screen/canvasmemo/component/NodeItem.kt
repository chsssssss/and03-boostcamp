package com.boostcamp.and03.ui.screen.canvasmemo.component

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.boostcamp.and03.R
import com.boostcamp.and03.data.model.request.ProfileType
import com.boostcamp.and03.ui.component.IconBadge
import com.boostcamp.and03.ui.screen.bookdetail.component.DropdownMenuContainer
import com.boostcamp.and03.ui.theme.And03IconSize
import com.boostcamp.and03.ui.theme.And03Padding
import com.boostcamp.and03.ui.theme.And03Spacing
import com.boostcamp.and03.ui.theme.And03Theme
import com.boostcamp.and03.ui.util.random

private object NodeItemValues {
    val width = 180.dp
    val height = 240.dp

    val borderWidth = 2.dp
    val iconAreaHeight = 48.dp

    const val TITLE_MAX_LINES = 1
    const val CONTENT_MAX_LINES = 6
}

@Composable
fun NodeItem(
    profileType: ProfileType,
    iconColor: Color?,
    imageUri: String?,
    title: String,
    content: String,
    isHighlighted: Boolean,
    modifier: Modifier = Modifier
) {
    val borderColor = if (isHighlighted) {
        And03Theme.colors.primary
    } else {
        And03Theme.colors.outline
    }

    Box(
        modifier = modifier
            .size(NodeItemValues.width, NodeItemValues.height)
            .background(
                And03Theme.colors.surface,
                And03Theme.shapes.defaultCorner
            )
            .border(
                NodeItemValues.borderWidth,
                borderColor,
                And03Theme.shapes.defaultCorner
            )
    ) {


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = And03Padding.PADDING_L,
                    start = And03Padding.PADDING_M,
                    end = And03Padding.PADDING_M,
                    bottom = And03Padding.PADDING_M
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .height(NodeItemValues.iconAreaHeight),
                contentAlignment = Alignment.Center
            ) {
                // 프로필 아이콘
                when (profileType) {
                    ProfileType.COLOR -> {
                        IconBadge(
                            iconResId = R.drawable.ic_person_filled,
                            iconColor = iconColor ?: Color.random(),
                            contentDescription = stringResource(
                                id = R.string.character_card_profile_icon_cd,
                                title
                            ),
                            size = And03IconSize.ICON_SIZE_L
                        )
                    }

                    ProfileType.IMAGE -> {
                        AsyncImage(
                            model = imageUri,
                            contentDescription = "인물 이미지",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(And03IconSize.ICON_SIZE_L),
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(And03Spacing.SPACE_S))

            Text(
                text = title,
                style = And03Theme.typography.titleMedium,
                maxLines = NodeItemValues.TITLE_MAX_LINES,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(And03Spacing.SPACE_S))

            Text(
                modifier = Modifier.weight(1f),
                text = content,
                style = And03Theme.typography.bodyMedium,
                color = And03Theme.colors.onSurfaceVariant,
                maxLines = NodeItemValues.CONTENT_MAX_LINES,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NodeItemPreview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(And03Spacing.SPACE_L),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NodeItem(
            profileType = ProfileType.COLOR,
            iconColor = Color(0xFF1E88E5),
            imageUri = "",
            title = "노드 제목",
            content = "짧은 내용",
            isHighlighted = false
        )

        NodeItem(
            profileType = ProfileType.COLOR,
            iconColor = Color(0xFF1E88E5),
            imageUri = "",
            title = "노드 제목",
            content = """
                가나다라가나다라가나다라가나다라가나다라가나다라가
                나다라가나다라가나다라가나다라가나
                다라가나다라가나다라가나다라가나다라가
                나다라가나다라가나다라
            """.trimIndent(),
            isHighlighted = true
        )
    }
}
