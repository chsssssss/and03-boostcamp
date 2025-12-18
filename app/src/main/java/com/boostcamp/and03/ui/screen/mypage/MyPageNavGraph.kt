package com.boostcamp.and03.ui.screen.mypage

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.boostcamp.and03.ui.navigation.MainTabRoute

fun NavGraphBuilder.myPageNavGraph(
    modifier: Modifier = Modifier
) {
    composable<MainTabRoute.MyPage> {
        MyPageScreen()
    }
}