package com.boostcamp.and03.ui.core.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.boostcamp.and03.R
import com.boostcamp.and03.ui.core.navigation.MainBottomTab
import kotlinx.collections.immutable.ImmutableList

private object MainBottomBarDimens {
    const val DURATION_MILLIS = 300
}

@Composable
fun MainBottomBar(
    visible: Boolean,
    tabs: ImmutableList<MainBottomTab>,
    currentTab: MainBottomTab?,
    onTabSelected: (MainBottomTab) -> Unit,
    modifier: Modifier = Modifier,
) {
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(
            initialOffsetY = { it },
            animationSpec = tween(durationMillis = MainBottomBarDimens.DURATION_MILLIS)
        ),
        exit = slideOutVertically(
            targetOffsetY = { it },
            animationSpec = tween(durationMillis = MainBottomBarDimens.DURATION_MILLIS)
        )
    ) {
        NavigationBar(
            modifier = modifier
        ) {
            tabs.forEach { tab ->
                NavigationBarItem(
                    selected = tab == currentTab,
                    onClick = { onTabSelected(tab) },
                    label = {
                        Text(
                            text = "테스트 라벨 텍스트"
                        )
                    },
                    icon = {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_android),
                            contentDescription = null
                        )
                    }
                )
            }
        }
    }
}