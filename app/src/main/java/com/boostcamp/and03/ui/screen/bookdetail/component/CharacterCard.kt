package com.boostcamp.and03.ui.screen.bookdetail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.boostcamp.and03.R
import com.boostcamp.and03.ui.component.IconBadge
import com.boostcamp.and03.ui.component.LabelChip

@Composable
fun CharacterCard(
    name: String,
    role: String,
    iconColor: Color,
    description: String,
    onMoreClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = Color.White,
                shape = RoundedCornerShape(12.dp)
            )
            .border(
                width = 1.dp,
                color = Color(0xFFE0E0E0),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(12.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 프로필 아이콘
                IconBadge(
                    iconResId = R.drawable.ic_person_filled,
                    iconColor = iconColor,
                    contentDescription = "$name profile",
                    size = 48.dp
                )

                Spacer(modifier = Modifier.width(8.dp))

                // 이름 + 역할
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = name,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    LabelChip(text = role)
                }

                IconButton(onClick = onMoreClick) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_more_vert_filled),
                        contentDescription = "more"
                    )
                }
            }

            // 설명
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF616161)
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun CharacterCardPreview() {
    CharacterCard(
        name = "Character Name",
        role = "Character Role",
        iconColor = Color(0xFF1E88E5),
        description = "Character Description",
        onMoreClick = {}
    )
}