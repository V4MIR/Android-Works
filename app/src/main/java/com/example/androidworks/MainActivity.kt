package com.example.androidworks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.androidworks.navigation.AppNavHost
import com.example.androidworks.ui.theme.AndroidWorksTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidWorksTheme {
                AppNavHost()
            }
        }
    }
}