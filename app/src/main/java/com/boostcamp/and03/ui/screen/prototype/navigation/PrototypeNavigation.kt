package com.boostcamp.and03.ui.screen.prototype.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.boostcamp.and03.ui.screen.prototype.model.MemoGraph
import com.boostcamp.and03.ui.screen.prototype.model.MemoNode
import com.boostcamp.and03.ui.screen.prototype.screen.CanvasScreen
import com.boostcamp.and03.ui.screen.prototype.screen.MemoEditScreen
import kotlinx.serialization.Serializable

@Composable
fun PrototypeNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = PrototypeRoute.Canvas
    ) {
        composable<PrototypeRoute.Canvas> {
            CanvasScreen(
                navController = navController,
//                items = items,
//                onItemsChange = { items = it },
//                onEditMemoClick = {
//                    navController.navigate(PrototypeRoute.MemoEdit)
//                }
            )
        }

        composable<PrototypeRoute.MemoEdit> {
            MemoEditScreen(
                onBack = { navController.popBackStack() },
                onSave = { title, content ->
                    navController.previousBackStackEntry!!
                        .savedStateHandle["new_memo"] = Pair(title, content)

                    navController.popBackStack()
                }
            )
        }
    }
}

sealed interface PrototypeRoute {

    @Serializable
    data object Canvas: PrototypeRoute

    @Serializable
    data object MemoEdit: PrototypeRoute
}