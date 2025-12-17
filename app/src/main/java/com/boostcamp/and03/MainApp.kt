package com.boostcamp.and03

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.boostcamp.and03.ui.component.MainBottomBar
import com.boostcamp.and03.ui.navigation.MainNavHost
import com.boostcamp.and03.ui.navigation.MainNavigator

@Composable
fun MainApp(
    navigator: MainNavigator,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        bottomBar = {
            MainBottomBar(
                visible = navigator.isShowBottomBar,
                tabs = navigator.mainBottomTabs,
                currentTab = navigator.currentTab,
                onTabSelected = { tab -> navigator.navigate(tab.route) }
            )
        },
        // contentWindowInsets = WindowInsets() // 최상위 Scaffold에서 WindowInset을 소비하지 않게 하여 Screen의 WindowInset만 소비할 수 있도록 함
    ) { innerPadding -> // 이 innerPadding은 탑바/바텀바의 영역만 갖고 옴
        MainNavHost(
            navigator = navigator,
            paddingValues = innerPadding
        )
    }
}