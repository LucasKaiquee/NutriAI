package com.example.nutriai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.nutriai.data.UserRepository
import com.example.nutriai.ui.screens.MainScreen
import com.example.nutriai.ui.theme.NutriAITheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            NutriAITheme {
                MainScreen()
            }

        }
    }
}