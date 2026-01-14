package com.boostcamp.and03

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.boostcamp.and03.ui.component.AddQuoteBottomSheet
import com.boostcamp.and03.ui.navigation.rememberMainNavigator
import com.boostcamp.and03.ui.screen.bookdetail.model.QuoteUiModel
import com.boostcamp.and03.ui.theme.And03Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            And03Theme {
                AddQuoteBottomSheet(quotes = dummyQuotes)
            }
        }
    }
}

val dummyQuotes = List(10) { i ->
    QuoteUiModel(
        id = i.toString(),
        content = "이 책을 읽으면서 꿈에 대한 새로운 관점을 얻게 되었다. $i",
        page = 10 + i,
        date = "2024.01.10"
    )
}
