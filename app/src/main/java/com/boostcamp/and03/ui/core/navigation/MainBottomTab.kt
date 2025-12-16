package com.boostcamp.and03.ui.core.navigation

import com.boostcamp.and03.R
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

enum class MainBottomTab(
    @param:DrawableRes val selectedIconId: Int,
    @param:DrawableRes val unselectedIconId: Int,
    @param:StringRes val titleTextId: Int,
    val route: MainTabRoute
) {
    BOOKLIST(
        R.drawable.ic_collections_bookmark_filled,
        R.drawable.ic_collections_bookmark_outlined,
        R.string.bottom_tab_booklist,
        MainTabRoute.Booklist
    ),
    ADD_BOOK(
        R.drawable.ic_library_add_filled,
        R.drawable.ic_library_add_outlined,
        R.string.bottom_tab_add_book,
        MainTabRoute.AddBook
    ),
    MY_PAGE(
        R.drawable.ic_account_filled,
        R.drawable.ic_account_outlined,
        R.string.bottom_tab_my_page,
        MainTabRoute.MyPage
    )
}