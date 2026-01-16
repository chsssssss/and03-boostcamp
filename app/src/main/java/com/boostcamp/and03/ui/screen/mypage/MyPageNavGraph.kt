package com.boostcamp.and03.ui.screen.mypage

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.boostcamp.and03.ui.navigation.Route

fun NavGraphBuilder.myPageNavGraph(
    modifier: Modifier = Modifier
) {
    composable<Route.MyPage> {
        MyPageScreen()
    }
}