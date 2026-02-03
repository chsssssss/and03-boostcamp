package com.boostcamp.and03.ui.screen.canvasmemo.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.boostcamp.and03.R
import com.boostcamp.and03.ui.component.IconBadge
import com.boostcamp.and03.ui.theme.And03IconSize
import com.boostcamp.and03.ui.theme.And03Padding
import com.boostcamp.and03.ui.theme.And03Theme
import androidx.compose.ui.graphics.Color
import com.boostcamp.and03.data.model.request.ProfileType
import com.boostcamp.and03.ui.util.random

@Composable
fun PersonImagePlaceholder(
    profileType: ProfileType,
    imageUrl: String?,
    iconColor: Color,
    onClick: (() -> Unit)? = null,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box {
            when (profileType) {
                ProfileType.COLOR -> {
                    IconBadge(
                        modifier = Modifier
                            .padding(top = And03Padding.PADDING_XL)
                            .clip(CircleShape)
                            .let {
                                if (onClick == null) {
                                    it
                                } else {
                                    it.clickable(onClick = onClick)
                                }
                            },
                        iconResId = R.drawable.ic_person_filled,
                        iconColor = iconColor,
                        contentDescription = stringResource(
                            id = R.string.content_description_character_basic_icon
                        ),
                        size = And03IconSize.ICON_SIZE_XL
                    )
                }
                ProfileType.IMAGE -> {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = "인물 이미지",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(And03IconSize.ICON_SIZE_XL)
                            .let {
                                if (onClick == null) {
                                    it
                                } else {
                                    it.clickable(onClick = onClick)
                                }
                            }
                    )
                }
            }
            if (onClick != null) {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .align(Alignment.BottomEnd)
                        .offset(x = 4.dp, y = 4.dp)
                        .clip(CircleShape)
                        .background(And03Theme.colors.surfaceVariant),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.CameraAlt,
                        contentDescription = "프로필 사진 변경",
                        tint = And03Theme.colors.onSurfaceVariant,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

        }

    }

}
