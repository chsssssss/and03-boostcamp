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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import com.boostcamp.and03.ui.navigation.MainBottomTab
import kotlinx.collections.immutable.ImmutableList

private object MainBottomBarDimens {
    const val DURATION_MILLIS = 300
}

@Composable
fun MainBottomBar(
    visible: Boolean,
    bottomTabs: ImmutableList<MainBottomTab>,
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
            bottomTabs.forEach { tab ->
                val isSelected = tab == currentTab
                val iconId = if (isSelected) {
                    tab.selectedIconId
                } else {
                    tab.unselectedIconId
                }

                NavigationBarItem(
                    selected = isSelected,
                    onClick = { onTabSelected(tab) },
                    icon = {
                        Icon(
                            imageVector = ImageVector.vectorResource(iconId),
                            contentDescription = stringResource(tab.tabTextId)
                        )
                    },
                    label = { Text(text = stringResource(tab.tabTextId)) }
                )
            }
        }
    }
}