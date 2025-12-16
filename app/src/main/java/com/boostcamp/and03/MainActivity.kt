package com.boostcamp.and03

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.boostcamp.and03.ui.core.navigation.rememberMainNavigator
import com.boostcamp.and03.ui.theme.And03Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            And03Theme {
                val navigator = rememberMainNavigator()
                MainApp(navigator)
            }
        }
    }
}