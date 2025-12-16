package com.boostcamp.and03.ui.core.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

enum class MainBottomTab(
    @param:DrawableRes val selectedIconId: Int?,
    @param:DrawableRes val unselectedIconId: Int?,
    @param:StringRes val iconTextId: Int?,
    @param:StringRes val titleTextId: Int?,
    val route: MainTabRoute
) {
    BOOKLIST(
        null,
        null,
        null,
        null,
        MainTabRoute.Booklist
    ),
    ADDBOOK(
        null,
        null,
        null,
        null,
        MainTabRoute.AddBook
    ),
    MYPAGE(
        null,
        null,
        null,
        null,
        MainTabRoute.MyPage
    )
}