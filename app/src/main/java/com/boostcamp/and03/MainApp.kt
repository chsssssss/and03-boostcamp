package com.boostcamp.and03

import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.boostcamp.and03.ui.component.ActionSnackBarHost
import com.boostcamp.and03.ui.component.MainBottomBar
import com.boostcamp.and03.ui.navigation.MainNavHost
import com.boostcamp.and03.ui.navigation.MainNavigator
import kotlinx.coroutines.launch

@Composable
fun MainApp(
    navigator: MainNavigator,
    modifier: Modifier = Modifier
) {
    val snackBarHostState = remember { SnackbarHostState() }

    val scope = rememberCoroutineScope()

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
        snackbarHost = { ActionSnackBarHost(hostState = snackBarHostState) },
        // contentWindowInsets = WindowInsets() // 최상위 Scaffold에서 WindowInset을 소비하지 않게 하여 Screen의 WindowInset만 소비할 수 있도록 함
    ) { innerPadding -> // 이 innerPadding은 탑바/바텀바의 영역만 갖고 옴
        MainNavHost(
            navigator = navigator,
            paddingValues = innerPadding,
            onShowSnackBar = { message, actionLabel ->
                scope.launch {
                    snackBarHostState.showSnackbar(
                        message = message,
                        actionLabel = actionLabel
                    )
                }
            }
        )
    }
}